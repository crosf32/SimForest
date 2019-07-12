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
    private List<String[]> stats;
    private String[] lastStat;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private GrowingPropagation growingPropagation;
    private FirePropagation firePropagation;
    private BugPropagation bugPropagation;

    public ForestSimulator(Forest forest) {
        this.forest = forest;
        this.growingPropagation = new GrowingPropagation();
        this.firePropagation = new FirePropagation();
        this.bugPropagation = new BugPropagation();
        this.stats = new ArrayList<>();
        this.stats.add(new String[]{"Jeune pousse", "Arbuste", "Arbre", "Vide", "Feu", "Infecte"});
    }

    private void displayForConsole() {
        this.forest.displayForConsole();
    }

    void launchSimulation() {
        if(delay != 0) {
            Runnable r = () -> {
                compute();
                time++;
                if(time >= maxTime || cancelled) {
                    executorService.shutdown();
                }
            };

            executorService.scheduleWithFixedDelay(r, 0, delay, TimeUnit.SECONDS);
        } else {
            for(int i = 0; i < maxTime; i++) {
                compute();
                time = i;
            }
        }
    }

    public <T extends WindowUpdatable> void launchSimulation(T clazz) {
        if(delay != 0) {
            Runnable r = () -> {
                if(time >= maxTime || cancelled) {
                    cancelled = true;
                    executorService.shutdown();
                    return;
                }
                Set<Vegetal> updated = compute();
                if(updated.size() == 0) {
                    cancelled = true;
                    executorService.shutdown();
                }
                clazz.updateCells(updated);
                time++;
            };

            executorService.scheduleWithFixedDelay(r, 0, delay, TimeUnit.SECONDS);
        } else {
            Set<Vegetal> updated = new HashSet<>();
            for(int i = 0; i < maxTime; i++) {
                Set<Vegetal> localUpdated = compute();
                updated.addAll(localUpdated);
                if(updated.size() == 0) executorService.shutdown();
                time = i;
            }
            clazz.updateCells(updated);
            cancelled = true;
        }

        time = 0;
    }

    public void addStats(List<String[]> stats) {
        this.stats.addAll(stats);
    }

    private Set<Vegetal> compute() {
        forest.getCells().forEach(vegetal -> {
            firePropagation.propagate(vegetal);
            bugPropagation.propagate(vegetal);
            growingPropagation.propagate(vegetal);
        });

        computeCsv();

        return forest.getCells().stream().filter(Vegetal::updateState).collect(Collectors.toSet());
    }

    private void computeCsv() {
        int empty = 0;
        int young = 0;
        int shrub = 0;
        int tree = 0;
        int fire = 0;
        int infected = 0;

        for(Vegetal vegetal : forest.getCells()) {
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
                case FIRE:
                    fire++;
                    break;
                case INFECTED:
                    infected++;
                    break;
            }
        }

        int max = young + shrub + tree + empty + fire + infected;

        String[] strings = new String[]{getPercent(young, max), getPercent(shrub, max), getPercent(tree, max), getPercent(empty, max), getPercent(fire, max), getPercent(infected, max)};
        this.lastStat = strings;
        stats.add(strings);
    }

    private String getPercent(int number, int max) {
        double i = Math.round(((double) number/max) * 100.0) / 100.0;
        return String.valueOf(i);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ForestSimulator setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public ForestSimulator setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public List<String[]> getStats() {
        return stats;
    }

    public String[] getLastStat() {
        return lastStat;
    }
}
