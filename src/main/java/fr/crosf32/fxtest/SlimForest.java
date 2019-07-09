package fr.crosf32.fxtest;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;

public class SlimForest {

    public SlimForest() {
        System.out.println("-- SimForest --");

        Forest f = new ForestBuilder()
            .setAt(2, 2, VegetalState.TREE)
            .setAt(2, 3, VegetalState.TREE)
            .setAt(1, 2, VegetalState.YOUNG)
            .setAt(1, 3, VegetalState.TREE, SpecificState.INFECTED)
            .setAt(2, 4, VegetalState.TREE, SpecificState.FIRE)
            .get();

        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.displayForConsole();

        forestSimulator.setMaxTime(15)
        .setDelay(0)
        .launchSimulation();

        /*     new Thread(() -> javafx.application.Application.launch(Main.class)).start();*/
    }
}
