package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForestSimulatorTest {


    @Test
    void GrowSimulation() {

            Forest f = new ForestBuilder()
                    .setAt(2, 2, VegetalState.TREE)
                    .setAt(2, 1, VegetalState.TREE)
                    .setAt(2, 3, VegetalState.SHRUB)
                    .setAt(1, 2, VegetalState.YOUNG)
                    .get();

            assertNotNull(f.getCell(5, 5));
            assertEquals(f.getCell(2, 3).getState(), VegetalState.SHRUB);
            assertEquals(f.getCell(2, 2).getState(), VegetalState.TREE);
            assertEquals(f.getCell(1, 2).getState(), VegetalState.YOUNG);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(1, 1).getState(), VegetalState.YOUNG);
            forestSimulator.launchSimulation();
            assertEquals(f.getCell(1, 1).getState(), VegetalState.SHRUB);
            forestSimulator.launchSimulation();
            assertEquals(f.getCell(1, 1).getState(), VegetalState.BEFORE_TREE);
            forestSimulator.launchSimulation();
            assertEquals(f.getCell(1, 1).getState(), VegetalState.TREE);


    }

    @Test
    void infectSimulation() {

            Forest f = new ForestBuilder()

                    .setAt(2, 4, VegetalState.TREE, SpecificState.INFECTED)
                    .setAt(2, 3, VegetalState.YOUNG)
                    .get();

            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.INFECTED);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(2, 4).getState(),VegetalState.EMPTY);


    }
    @Test
    void infectPropagation() {

            Forest f = new ForestBuilder()
                    .setAt(2, 4, VegetalState.TREE, SpecificState.INFECTED)
                    .get();

            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.INFECTED);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(2, 4).getState(),VegetalState.EMPTY);


    }
    @Test
    void fireSimulation() {

            Forest f = new ForestBuilder()
                    .setAt(2, 4, VegetalState.TREE, SpecificState.FIRE)
                    .get();

            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.FIRE);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.ASH);
            forestSimulator.launchSimulation();
            assertEquals(f.getCell(2, 4).getState(), VegetalState.EMPTY);


    }
    @Test
    void firePropagation() {

            Forest f = new ForestBuilder()
                    .setAt(2, 4, VegetalState.TREE, SpecificState.FIRE)
                    .get();

            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.FIRE);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.ASH);
            forestSimulator.launchSimulation();
            assertEquals(f.getCell(2, 4).getState(), VegetalState.EMPTY);


    }
}