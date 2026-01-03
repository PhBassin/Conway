const createBtn = document.getElementById('createBtn');
const stepBtn = document.getElementById('stepBtn');
const refreshBtn = document.getElementById('refreshBtn');
const rowsInput = document.getElementById('rows');
const colsInput = document.getElementById('cols');
const randomCheckbox = document.getElementById('random');
const statusEl = document.getElementById('status');
const gridContainer = document.getElementById('gridContainer');
const delayInput = document.getElementById('delay');
const startAutoBtn = document.getElementById('startAutoBtn');
const stopAutoBtn = document.getElementById('stopAutoBtn');

let currentGrid = null;
let autoRunning = false;

function setStatus(text){ statusEl.textContent = text; }

function renderGrid(grid){
  currentGrid = grid;
  gridContainer.innerHTML = '';
  if (!grid) return;
  const cols = grid[0] ? grid[0].length : 1;
  gridContainer.style.gridTemplateColumns = `repeat(${cols}, 1fr)`;
  for(let r=0;r<grid.length;r++){
    const row = document.createElement('div');
    row.className = 'row';
    for(let c=0;c<grid[r].length;c++){
      const cell = document.createElement('div');
      cell.className = 'cell ' + (grid[r][c] ? 'alive' : 'dead');
      row.appendChild(cell);
    }
    gridContainer.appendChild(row);
  }
}

async function createGame(){
  const body = {
    rows: Number(rowsInput.value) || 20,
    cols: Number(colsInput.value) || 20,
    random: randomCheckbox.checked
  };
  setStatus('Création en cours...');
  try{
    const res = await fetch('/api/game', {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify(body)});
    if (!res.ok) throw new Error(res.statusText);
    const grid = await res.json();
    renderGrid(grid);
    setStatus('Grille créée — ' + grid.length + 'x' + (grid[0] ? grid[0].length : '?'));
  }catch(e){ setStatus('Erreur création: '+e.message); }
}

async function step(){
  setStatus('Avance génération...');
  try{
    const res = await fetch('/api/game/step', {method:'POST'});
    if (!res.ok) throw new Error(res.statusText);
    const grid = await res.json();
    renderGrid(grid);
    setStatus('Génération avancée');
  }catch(e){ setStatus('Erreur step: '+e.message); }
}

async function refresh(){
  setStatus('Rafraîchissement...');
  try{
    const res = await fetch('/api/game');
    if (!res.ok) throw new Error(res.statusText);
    const grid = await res.json();
    renderGrid(grid);
    setStatus('Grille chargée');
  }catch(e){ setStatus('Aucune grille disponible'); }
}

createBtn.addEventListener('click', createGame);
stepBtn.addEventListener('click', step);
refreshBtn.addEventListener('click', refresh);
startAutoBtn.addEventListener('click', startAuto);
stopAutoBtn.addEventListener('click', stopAuto);

// Try to load existing grid on open
refresh();

function sleep(ms){ return new Promise(res => setTimeout(res, ms)); }

async function startAuto(){
  if (autoRunning) return;
  autoRunning = true;
  startAutoBtn.disabled = true;
  stopAutoBtn.disabled = false;
  createBtn.disabled = true;
  stepBtn.disabled = true;
  refreshBtn.disabled = true;
  setStatus('Auto: démarrage...');
  const delay = Math.max(1, Number(delayInput.value) || 500);
  try{
    while(autoRunning){
      await step();
      if (!autoRunning) break;
      await sleep(delay);
    }
    setStatus('Auto terminé');
  }catch(e){ setStatus('Auto erreur: '+e.message); }
  stopAuto();
}

function stopAuto(){
  autoRunning = false;
  startAutoBtn.disabled = false;
  stopAutoBtn.disabled = true;
  createBtn.disabled = false;
  stepBtn.disabled = false;
  refreshBtn.disabled = false;
  setStatus('Auto arrêté');
}
