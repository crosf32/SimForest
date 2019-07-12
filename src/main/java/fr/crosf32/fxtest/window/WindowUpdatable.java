package fr.crosf32.fxtest.window;

import fr.crosf32.fxtest.entity.Vegetal;

import java.util.Set;

public interface WindowUpdatable {

    void updateCells(Set<Vegetal> vegetals);
    void updateTimer(int time);
}
