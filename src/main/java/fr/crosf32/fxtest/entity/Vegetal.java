package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Set;

public class Vegetal extends Cell {

    private VegetalState state = VegetalState.EMPTY;
    private SpecificState specificState;

    private Set<Vegetal> neighbours;

    public Vegetal(int row, int col) {
        super(row, col);
    }

    public VegetalState getState() {
        return state;
    }

    public Vegetal setState(VegetalState state) {
        this.state = state;
        return this;
    }

    public SpecificState getSpecificState() {
        return specificState;
    }

    public void setSpecificState(SpecificState specificState) {
        this.specificState = specificState;
    }

    public Set<Vegetal> getNeighbours() {
        return neighbours;
    }

    void setNeighbours(Set<Vegetal> neighbours) {
        this.neighbours = neighbours;
    }
}
