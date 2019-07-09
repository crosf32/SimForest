package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.propagation.GrowingPropagation;

public class ForestSimulator {
    private Forest forest;
    private int time = 0;
    private int maxTime;

    private GrowingPropagation growingPropagation;

    public ForestSimulator(Forest forest) {
        this.forest = forest;
        this.growingPropagation = new GrowingPropagation(forest);
    }

    public void displayForConsole() {
        this.forest.displayForConsole();
    }

    public void launchSimulation() {
        for(int i = time; i < maxTime; i++) {
            for(Vegetal c : forest.getCells()) {
                growingPropagation.propagate(c);
            }
            // insectAttack Simulation()
            // fireAttack Simulation()
        }
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    // TODO : Check executeSimulationAt utility
}
