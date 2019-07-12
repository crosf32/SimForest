package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ForestSimulatorTest {
    Forest f;

    ForestSimulatorTest() {
        f = new ForestBuilder()
                .setAt(2, 2, VegetalState.TREE)
                .setAt(2, 1, VegetalState.TREE)
                .setAt(2, 3, VegetalState.SHRUB)
                .setAt(1, 2, VegetalState.YOUNG)
                .get();
    }

    @Test
    void timeIsCorrest() {
        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.setDelay(0).setMaxTime(5).launchSimulation();
        assertEquals(4, forestSimulator.getTime());
        forestSimulator.setDelay(1).setMaxTime(2).launchSimulation();
        Runnable r = () -> assertEquals(1, forestSimulator.getTime());
        Executors.newScheduledThreadPool(1).schedule(r, 2, TimeUnit.SECONDS);
    }

    @Test
    void GrowSimulation() {
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
}
