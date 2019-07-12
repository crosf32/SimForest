package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Set;

public  class Vegetal extends Cell {

    private VegetalState state = VegetalState.EMPTY;
    private VegetalState lastState = VegetalState.EMPTY;

    private Set<Vegetal> neighbours;

    public Vegetal(int row, int col) {
        super(row, col);
    }

    public VegetalState getState() {
        return state;
    }

    public Vegetal setState(VegetalState state) {
        this.setLastState(this.state);
        this.state = state;
        return this;
    }

    public void paint() {
        super.setStyle("-fx-background-color: " + getColor());
    }

    public boolean updateState() {
        if(this.getState() != this.getLastState()) {
            this.setLastState(getState());
            return true;
        }
        return false;
    }

    public String getColor() {
        return getState().getColor();
    }

    public Set<Vegetal> getNeighbours() {
        return neighbours;
    }

    void setNeighbours(Set<Vegetal> neighbours) {
        this.neighbours = neighbours;
    }

    public VegetalState getLastState() {
        return lastState;
    }

    public Vegetal setLastState(VegetalState lastState) {
        this.lastState = lastState;
        return this;
    }
}
