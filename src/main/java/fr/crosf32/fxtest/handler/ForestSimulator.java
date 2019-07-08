package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;

public class ForestSimulator {
    private Forest forest;
    private int time = 0;
    private int maxTime;

    public ForestSimulator(Forest forest) {
        this.forest = forest;
    }

    public void displayForConsole() {
        this.forest.displayForConsole();
    }

    public void launchSimulation() {
        for(int i = time; i < maxTime; i++) {
            // growingSimulation();
            // insectAttack Simulation()
            // fireAttack Simulation()
        }
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    // TODO : Check executeSimulationAt utility
}
