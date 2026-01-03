package com.opelkad.conway.v1.game;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

        @Test
        void controllerCreateGetStep() {
                GameService service = new GameService();
                GameController controller = new GameController(service);

                CreateGameRequest req = new CreateGameRequest();
                req.setRows(5);
                req.setCols(5);
                req.setRandom(false);

                ResponseEntity<boolean[][]> create = controller.create(req);
                assertEquals(201, create.getStatusCode().value());
                assertNotNull(create.getBody());

                ResponseEntity<boolean[][]> step = controller.step();
                assertEquals(200, step.getStatusCode().value());

                ResponseEntity<boolean[][]> get = controller.get();
                assertEquals(200, get.getStatusCode().value());
        }
}
