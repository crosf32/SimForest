package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;

public class ForestWindowManager {
    private Forest forest;
    private ForestBuilder forestBuilder;
    private ForestSimulator forestSimulator;

    public ForestWindowManager(int width, int height) {
        forest = new Forest(width, height);
        forestBuilder = new ForestBuilder();
        forestSimulator = new ForestSimulator(forest);
    }

    public Forest getForest() {
        return forest;
    }

    public ForestBuilder getForestBuilder() {
        return forestBuilder;
    }

    public void setForestBuilder(ForestBuilder forestBuilder) {
        this.forestBuilder = forestBuilder;
    }

    public ForestSimulator getForestSimulator() {
        return forestSimulator;
    }

    public void setForestSimulator(ForestSimulator forestSimulator) {
        this.forestSimulator = forestSimulator;
    }
}
