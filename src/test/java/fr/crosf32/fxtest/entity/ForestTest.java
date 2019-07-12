package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.handler.ForestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForestTest {

    Forest f;

    ForestTest() {
        f = new ForestBuilder().randomGeneration().get();
    }

    @Test
    void getCell() {
        assertNotNull(f.getCell(0, 0));
    }

    @Test
    void calcNeibhours() {
        assertEquals(f.getCell(0, 0).getNeighbours().size(), 3);
        assertEquals(f.getCell(1, 1).getNeighbours().size(), 8);
    }
}