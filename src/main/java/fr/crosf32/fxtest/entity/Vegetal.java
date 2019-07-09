package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Set;

public class Vegetal extends Cell {

    private VegetalState state = VegetalState.EMPTY;
    private VegetalState lastState = VegetalState.EMPTY;
    private SpecificState specificState = SpecificState.NONE;
    private SpecificState lastSpecificState = SpecificState.NONE;

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

    public SpecificState getSpecificState() {
        return specificState;
    }

    public Vegetal setSpecificState(SpecificState specificState) {
        setLastSpecificState(this.specificState);
        this.specificState = specificState;
        return this;
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

    public SpecificState getLastSpecificState() {
        return lastSpecificState;
    }

    public Vegetal setLastSpecificState(SpecificState lastSpecificState) {
        this.lastSpecificState = lastSpecificState;
        return this;
    }

    public void updateState() {
        this.setLastState(getState());
        this.setLastSpecificState(getSpecificState());
    }
}
