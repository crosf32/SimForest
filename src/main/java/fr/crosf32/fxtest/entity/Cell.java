package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

public class Cell {
    private VegetalState state = VegetalState.EMPTY;
    private SpecificState specificState;
    private int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public VegetalState getState() {
        return state;
    }

    public Cell setState(VegetalState state) {
        this.state = state;
        return this;
    }

    public SpecificState getSpecificState() {
        return specificState;
    }

    public void setSpecificState(SpecificState specificState) {
        this.specificState = specificState;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
