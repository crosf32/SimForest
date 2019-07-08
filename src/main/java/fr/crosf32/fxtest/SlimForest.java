package fr.crosf32.fxtest;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;

public class SlimForest {

    public SlimForest() {
        System.out.println("-- SimForest --");

        Forest f = new ForestBuilder().randomGeneration().get();

        ForestSimulator forestSimulator = new ForestSimulator(f);

        forestSimulator.setMaxTime(4);

        forestSimulator.launchSimulation(); // TODO : Do this x)

        forestSimulator.displayForConsole();

   /*     new Thread(() -> javafx.application.Application.launch(Main.class)).start();*/
    }
}
