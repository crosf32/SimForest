package fr.crosf32.fxtest;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.sample.Main;

public class SlimForest {

    public SlimForest() {
        new Thread(() -> javafx.application.Application.launch(Main.class)).start();
        System.out.println("-- SimForest --");

        Forest f = new ForestBuilder()
            .setAt(2, 2, VegetalState.TREE)
            .setAt(2, 3, VegetalState.TREE)
            .setAt(1, 2, VegetalState.YOUNG)
            .get();

        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.displayForConsole();

        forestSimulator.setMaxTime(1)
        .setDelay(0)
        .launchSimulation();
    }
}
