package com.opelkad.conway.v1.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game current;

    public synchronized Game create(int rows, int cols, boolean random, double aliveProbability, long seed) {
        current = new Game(rows, cols);
        if (random) current.randomize(seed, aliveProbability);
        return current;
    }

    public synchronized boolean[][] getGrid() {
        if (current == null) return null;
        return current.getGrid();
    }

    public synchronized boolean[][] step() {
        if (current == null) return null;
        current.step();
        return current.getGrid();
    }

    public synchronized void setCell(int r, int c, boolean alive) {
        if (current == null) return;
        current.setCell(r, c, alive);
    }
}
