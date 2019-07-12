package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.propagation.BugPropagation;
import fr.crosf32.fxtest.propagation.FirePropagation;
import fr.crosf32.fxtest.propagation.GrowingPropagation;
import fr.crosf32.fxtest.window.WindowUpdatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ForestSimulator {
    private Forest forest;
    private int time = 0;
    private int maxTime, delay;
    private boolean cancelled = false;
    private List<String[]> stats = new ArrayList<>();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private GrowingPropagation growingPropagation;
    private FirePropagation firePropagation;
    private BugPropagation bugPropagation;

    public ForestSimulator(Forest forest) {
        this.forest = forest;
        this.growingPropagation = new GrowingPropagation();
        this.firePropagation = new FirePropagation();
        this.bugPropagation = new BugPropagation();
        stats.add(new String[]{"Jeune pousse", "Arbuste", "Arbre", "Vide"});
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
                if(time >= maxTime || cancelled) {
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

    public <T extends WindowUpdatable> void launchSimulation(T clazz) {
        if(delay != 0) {
            Runnable r = () -> {
                if(time >= maxTime || cancelled) {
                    executorService.shutdown();
                    return;
                }
                Set<Vegetal> updated = calculate();
                if(updated.size() == 0) executorService.shutdown();
                clazz.updateTimer((time+1));
                clazz.updateCells(updated);
                time++;
            };

            executorService.scheduleWithFixedDelay(r, 0, delay, TimeUnit.SECONDS);
        } else {
            Set<Vegetal> updated = new HashSet<>();
            for(int i = 0; i < maxTime; i++) {
                Set<Vegetal> localUpdated = calculate();
                updated.addAll(localUpdated);
                if(updated.size() == 0) executorService.shutdown();
                time = i;
            }
            clazz.updateTimer((time+1));
            clazz.updateCells(updated);
        }

        time = 0;
    }

    public ForestSimulator setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public void computeCsv() {
        int empty = 0;
        int young = 0;
        int shrub = 0;
        int tree = 0;

        for(Vegetal vegetal : forest.getCells()) {
            if(vegetal.getState().getRiskOfFire() == -1) {
                continue;
            }

            switch(vegetal.getState()) {
                case EMPTY:
                    empty++;
                    break;
                case SHRUB:
                    shrub++;
                    break;
                case TREE:
                    tree++;
                    break;
                case YOUNG:
                    young++;
                    break;
                case BEFORE_TREE:
                    shrub++;
                    break;
            }
        }

        int max = young + shrub + tree + empty;

        String[] strings = new String[]{(young*100/max) + "%", (shrub*100/max) + "%", (tree*100/max) + "%", (empty*100/max) +"%"};
        stats.add(strings);
    }

    public ForestSimulator setTime(int time) {
        this.time = time;
        return this;
    }

    public ForestSimulator setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public Set<Vegetal> calculate() {
        forest.getCells().forEach(vegetal -> {
            firePropagation.propagate(vegetal);
            bugPropagation.propagate(vegetal);
            growingPropagation.propagate(vegetal);
        });

        computeCsv();

        return forest.getCells().stream().filter(Vegetal::updateState).collect(Collectors.toSet());
    }

    public FirePropagation getFirePropagation() {
        return firePropagation;
    }

    public BugPropagation getBugPropagation() {
        return bugPropagation;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public List<String[]> getStats() {
        return stats;
    }

    public void setStats(List<String[]> stats) {
        this.stats = stats;
    }
}
