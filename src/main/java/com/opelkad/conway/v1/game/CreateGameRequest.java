package com.opelkad.conway.v1.game;

public class CreateGameRequest {
    private int rows;
    private int cols;
    private boolean random = false;
    private double aliveProbability = 0.3;
    private long seed = 0L;

    public CreateGameRequest() {}

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public double getAliveProbability() {
        return aliveProbability;
    }

    public void setAliveProbability(double aliveProbability) {
        this.aliveProbability = aliveProbability;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
