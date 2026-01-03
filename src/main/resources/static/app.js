const createBtn = document.getElementById('createBtn');
const stepBtn = document.getElementById('stepBtn');
const refreshBtn = document.getElementById('refreshBtn');
const rowsInput = document.getElementById('rows');
const colsInput = document.getElementById('cols');
const randomCheckbox = document.getElementById('random');
const statusEl = document.getElementById('status');
const gridContainer = document.getElementById('gridContainer');

let currentGrid = null;

function setStatus(text){ statusEl.textContent = text; }

function renderGrid(grid){
  currentGrid = grid;
  gridContainer.innerHTML = '';
  if (!grid) return;
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

// Try to load existing grid on open
refresh();
