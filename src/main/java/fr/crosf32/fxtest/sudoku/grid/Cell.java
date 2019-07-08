package fr.crosf32.fxtest.sudoku.grid;

import java.util.ArrayList;
import java.util.List;

public class Cell implements Cloneable{

    private int value;
    private int row;
    private int column;
    private List<Integer> possibleValues;
    private int guessValue;

    public Cell(int row, int column, int value) {
        this.value = value;
        this.row = row;
        this.column = column;
        this.guessValue = 0;
        this.possibleValues = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(List<Integer> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void addPossibleValue(int value) {
        this.possibleValues.add(value);
    }

    public void removePossibleValue(int value) {
        if(this.possibleValues.contains(value)) {
            this.possibleValues.add(value);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getGuessValue() {
        return guessValue;
    }

    public void setGuessValue(int guessValue) {
        this.guessValue = guessValue;
    }
}
