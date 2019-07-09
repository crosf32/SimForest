package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForestSimulatorTest {

    @Test
    void displayForConsole() {
    }

    @Test
    void launchSimulation() {
        Forest f = new ForestBuilder()
                .setAt(2, 2, VegetalState.TREE)
                .setAt(2, 3, VegetalState.TREE)
                .setAt(1, 2, VegetalState.YOUNG)
                .get();

        assertNotNull(f.getCell(5, 5));
        assertNull(f.getCell(55, 5));
    }

    @Test
    void setMaxTime() {
    }
}