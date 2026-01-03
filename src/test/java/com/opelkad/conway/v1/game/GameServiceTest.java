package com.opelkad.conway.v1.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    @Test
    void createAndStep() {
        GameService service = new GameService();
        service.create(5, 5, false, 0.0, 0L);

        service.setCell(2, 1, true);
        service.setCell(2, 2, true);
        service.setCell(2, 3, true);

        service.step();

        boolean[][] grid = service.getGrid();
        assertTrue(grid[1][2]);
        assertTrue(grid[2][2]);
        assertTrue(grid[3][2]);
    }
}
