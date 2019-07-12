package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForestBuilderTest {
    Forest f;

    @Test
    void randomGeneration() {
        f = new ForestBuilder().randomGeneration().get();
        int trees = getNumberByState(VegetalState.TREE);
        int shrub = getNumberByState(VegetalState.SHRUB);
        int young = getNumberByState(VegetalState.YOUNG);
        int fire = getNumberByState(VegetalState.FIRE);
        int infected = getNumberByState(VegetalState.INFECTED);

        assertTrue((trees+shrub+young) != 0);
        assertTrue(fire != 0);
        assertTrue(infected != 0);
    }

    private int getNumberByState(VegetalState state) {
        return f.getCells().stream().filter(vegetal -> vegetal.getState() == state).toArray().length;
    }
}