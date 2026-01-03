package com.opelkad.conway.v1.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public ResponseEntity<boolean[][]> create(@RequestBody CreateGameRequest req) {
        if (req.getRows() <= 0 || req.getCols() <= 0) return ResponseEntity.badRequest().build();
        service.create(req.getRows(), req.getCols(), req.isRandom(), req.getAliveProbability(), req.getSeed());
        boolean[][] grid = service.getGrid();
        return ResponseEntity.status(HttpStatus.CREATED).body(grid);
    }

    @GetMapping("/game")
    public ResponseEntity<boolean[][]> get() {
        boolean[][] grid = service.getGrid();
        if (grid == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(grid);
    }

    @PostMapping("/game/step")
    public ResponseEntity<boolean[][]> step() {
        boolean[][] grid = service.step();
        if (grid == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(grid);
    }

}
