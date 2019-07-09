package fr.crosf32.fxtest;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;

public class SlimForest {

    public SlimForest() {
        System.out.println("-- SimForest --");

        Forest f = new ForestBuilder()
            .setAt(2, 2, VegetalState.TREE)
            .setAt(2, 3, VegetalState.TREE).get();

        ForestSimulator forestSimulator = new ForestSimulator(f);

        forestSimulator.setMaxTime(4);

        forestSimulator.displayForConsole();

        forestSimulator.launchSimulation();

        forestSimulator.displayForConsole();

        /*     new Thread(() -> javafx.application.Application.launch(Main.class)).start();*/
    }
}
