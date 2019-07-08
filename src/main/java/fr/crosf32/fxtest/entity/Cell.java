package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.VegetalState;

public class Cell {
    private VegetalState state = VegetalState.EMPTY;

    public VegetalState getState() {
        return state;
    }

    public Cell setState(VegetalState state) {
        this.state = state;
        return this;
    }
}
