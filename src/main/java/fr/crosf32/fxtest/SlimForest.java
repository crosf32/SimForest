package fr.crosf32.fxtest;

import fr.crosf32.fxtest.handler.FxWindowManager;
import fr.crosf32.fxtest.sample.Main;

public class SlimForest {

    private static SlimForest instance;

    private FxWindowManager fxWindowManager;

    public SlimForest() {
        instance = this;

        new Thread(() -> javafx.application.Application.launch(Main.class)).start();
        fxWindowManager = new FxWindowManager();

        System.out.println("-- SimForest --");

       /* Forest f = new ForestBuilder()
            .setAt(2, 2, VegetalState.TREE)
            .setAt(2, 3, VegetalState.TREE)
            .setAt(1, 2, VegetalState.YOUNG)
            .get();

        ForestSimulator forestSimulator = new ForestSimulator(f);
        forestSimulator.displayForConsole();

        forestSimulator.setMaxTime(1)
        .setDelay(0)
        .launchSimulation();*/
    }

    public FxWindowManager getFxWindowManager() {
        return fxWindowManager;
    }

    public static SlimForest getInstance() {
        return instance;
    }
}
