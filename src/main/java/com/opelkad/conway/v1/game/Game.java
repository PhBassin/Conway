package com.opelkad.conway.v1.game;

public class Game {
    private final int rows;
    private final int cols;
    private boolean[][] grid;

    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean[][] getGrid() {
        boolean[][] copy = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(grid[r], 0, copy[r], 0, cols);
        }
        return copy;
    }

    public void setCell(int r, int c, boolean alive) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return;
        grid[r][c] = alive;
    }

    public void randomize(long seed, double aliveProbability) {
        java.util.Random rnd = (seed == 0) ? new java.util.Random() : new java.util.Random(seed);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = rnd.nextDouble() < aliveProbability;
            }
        }
    }

    public void step() {
        boolean[][] next = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int neighbors = countNeighbors(r, c);
                if (grid[r][c]) {
                    next[r][c] = (neighbors == 2 || neighbors == 3);
                } else {
                    next[r][c] = (neighbors == 3);
                }
            }
        }
        this.grid = next;
    }

    private int countNeighbors(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr;
                int nc = c + dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc]) count++;
            }
        }
        return count;
    }
}
