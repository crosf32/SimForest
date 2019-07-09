package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.propagation.BugPropagation;
import fr.crosf32.fxtest.propagation.FirePropagation;
import fr.crosf32.fxtest.propagation.GrowingPropagation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ForestSimulator {
    private Forest forest;
    private int time = 0;
    private int maxTime, delay;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private GrowingPropagation growingPropagation;
    private FirePropagation firePropagation;
    private BugPropagation bugPropagation;

    public ForestSimulator(Forest forest) {
        this.forest = forest;
        this.growingPropagation = new GrowingPropagation();
        this.firePropagation = new FirePropagation();
        this.bugPropagation = new BugPropagation();
    }

    public void displayForConsole() {
        this.forest.displayForConsole();
    }

    public void launchSimulation() {
        if(delay != 0) {
            Runnable r = () -> {
                calculate();
                displayForConsole();
                time++;
                if(time >= maxTime) {
                    executorService.shutdown();
                }
            };

            executorService.scheduleWithFixedDelay(r, 0, delay, TimeUnit.SECONDS);
        } else {
            for(int i = 0; i < maxTime; i++) {
                calculate();
                displayForConsole();
            }
        }
    }

    public ForestSimulator setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public ForestSimulator setDelay(int delay) {
        this.delay = delay;
        return this;
    }


    private void calculate() {
        forest.getCells().forEach(vegetal -> {
            firePropagation.propagate(vegetal);
            bugPropagation.propagate(vegetal);
            growingPropagation.propagate(vegetal);
        });

        forest.getCells().forEach(Vegetal::updateState);
    }

    public FirePropagation getFirePropagation() {
        return firePropagation;
    }

    public void setFirePropagation(FirePropagation firePropagation) {
        this.firePropagation = firePropagation;
    }

    public BugPropagation getBugPropagation() {
        return bugPropagation;
    }

    public void setBugPropagation(BugPropagation bugPropagation) {
        this.bugPropagation = bugPropagation;
    }
}
