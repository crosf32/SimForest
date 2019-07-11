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

<<<<<<< HEAD
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
=======
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
>>>>>>> 4bd9d45ceefbcd7c3e372e446e4ff93130cc2dc1


    }

    @Test
    void infectSimulation() {
<<<<<<< HEAD

            Forest f = new ForestBuilder()

                    .setAt(2, 4, VegetalState.TREE, SpecificState.INFECTED)
                    .setAt(2, 3, VegetalState.YOUNG)
                    .get();

            assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.INFECTED);
            ForestSimulator forestSimulator = new ForestSimulator(f);
            forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
            assertEquals(f.getCell(2, 4).getState(),VegetalState.EMPTY);


=======
        checkProba(VegetalState.TREE, false);
        checkProba(VegetalState.SHRUB, false);
        checkProba(VegetalState.YOUNG, false);
>>>>>>> 4bd9d45ceefbcd7c3e372e446e4ff93130cc2dc1
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
<<<<<<< HEAD
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
=======
    void infectPropagation() {

        Forest f = new ForestBuilder()
                .setAt(2, 4, VegetalState.TREE, SpecificState.INFECTED)
                .get();

        assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.INFECTED);
        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
        assertEquals(f.getCell(2, 4).getState(), VegetalState.EMPTY);


    }

    @Test
    void fireSimulation() {
        checkProba(VegetalState.TREE, true);
        checkProba(VegetalState.SHRUB, true);
        checkProba(VegetalState.YOUNG, true);
    }

    private void checkProba(VegetalState state, boolean fire) {
        int nbrFire = 0;
        int nbrTestTrue = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {

                ForestBuilder fb = new ForestBuilder()
                        .setAt(2, 3, state);
                if(fire) {
                    fb.setAt(2, 4, VegetalState.TREE, SpecificState.FIRE);
                } else {
                    fb.setAt(2, 4, VegetalState.TREE, SpecificState.INFECTED);
                }

                Forest f = fb.get();

                ForestSimulator forestSimulator = new ForestSimulator(f);
                forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();

                if ((fire && f.getCell(2, 3).getSpecificState() == SpecificState.FIRE) || f.getCell(2, 3).getSpecificState() == SpecificState.INFECTED) {
                    nbrFire++;
                }


            }

            if(fire) {
                if (nbrFire >= (state.getRiskOfFire()-8)) nbrTestTrue +=1;
            } else {
                if (nbrFire >= (state.getRiskOfInfection()-8)) nbrTestTrue +=1;
            }

            nbrFire = 0;

        }
        assertTrue(nbrTestTrue >= 90);
    }
    @Test
    void firePropagation () {

        Forest f = new ForestBuilder()
                .setAt(2, 4, VegetalState.TREE, SpecificState.FIRE)
                .get();

        assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.FIRE);
        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
        assertEquals(f.getCell(2, 4).getSpecificState(), SpecificState.ASH);
        forestSimulator.launchSimulation();
        assertEquals(f.getCell(2, 4).getState(), VegetalState.EMPTY);
>>>>>>> 4bd9d45ceefbcd7c3e372e446e4ff93130cc2dc1


    }
}
