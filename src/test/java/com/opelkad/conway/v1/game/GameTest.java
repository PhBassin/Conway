package com.opelkad.conway.v1.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void blinkerOscillator() {
        Game g = new Game(5, 5);
        g.setCell(2, 1, true);
        g.setCell(2, 2, true);
        g.setCell(2, 3, true);

        g.step();

        boolean[][] grid = g.getGrid();
        assertTrue(grid[1][2], "cell (1,2) should be alive");
        assertTrue(grid[2][2], "cell (2,2) should be alive");
        assertTrue(grid[3][2], "cell (3,2) should be alive");

        assertFalse(grid[2][1], "cell (2,1) should be dead");
        assertFalse(grid[2][3], "cell (2,3) should be dead");
    }
}
