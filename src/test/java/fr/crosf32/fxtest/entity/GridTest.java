package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.handler.ForestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    @Test
    void getCells() {
        Forest f = new ForestBuilder().get();
        assertEquals(f.getCells().size(), (f.getWidth()*f.getHeight()));
    }
}