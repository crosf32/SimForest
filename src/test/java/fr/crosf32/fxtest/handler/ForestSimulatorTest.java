package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;
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
        checkProba(VegetalState.TREE, false);
        checkProba(VegetalState.SHRUB, false);
        checkProba(VegetalState.YOUNG, false);
    }
    @Test
    void infectPropagation() {

        Forest f = new ForestBuilder()
                .setAt(2, 4, VegetalState.TREE)
                .get();

        assertEquals(f.getCell(2, 4).getState(), VegetalState.INFECTED);
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
                    fb.setAt(2, 4, VegetalState.FIRE);
                } else {
                    fb.setAt(2, 4, VegetalState.INFECTED);
                }

                Forest f = fb.get();

                ForestSimulator forestSimulator = new ForestSimulator(f);
                forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();

                if ((fire && f.getCell(2, 3).getState() == VegetalState.FIRE) || f.getCell(2, 3).getState() == VegetalState.INFECTED) {
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
                .setAt(2, 4, VegetalState.FIRE)
                .get();

        assertEquals(f.getCell(2, 4).getState(), VegetalState.FIRE);
        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.setMaxTime(1).setDelay(0).launchSimulation();
        assertEquals(f.getCell(2, 4).getState(), VegetalState.ASH);
        forestSimulator.launchSimulation();
        assertEquals(f.getCell(2, 4).getState(), VegetalState.EMPTY);
    }
}
