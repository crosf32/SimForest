package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;

public class ForestBuilder {

    private Forest f;

    public ForestBuilder(Forest f) {
        this.f = f;
    }

    public ForestBuilder() {
        this.f = new Forest(10, 10);
    }

    public ForestBuilder randomGeneration() {
        int width = this.f.getWidth();
        int height = this.f.getHeight();

        int nbr = (width*height) / 100;

        for(int i = 0; i < nbr; i++) {
            int randomRow = new Random().nextInt(width);
            int randomCol = new Random().nextInt(height);

            VegetalState randomState = VegetalState.values()[new Random().nextInt(VegetalState.values().length)];
            setAt(randomRow, randomCol, randomState);
        }

        return this;
    }

    public void reset() {
        f.resetGrid();
    }

   /* private void save() {
        Set<Vegetal> changedCells = f.getCells().stream().filter(vegetal -> vegetal.getState() != VegetalState.EMPTY || vegetal.getSpecificState() != SpecificState.NONE).collect(Collectors.toSet());
        changedCells.forEach(cell -> {
            if(cell.getState() != VegetalState.EMPTY) {
                initVegetalStates.put(cell, cell.getState());
            }
            if(cell.getSpecificState() != SpecificState.NONE) {
                initSpecificStates.put(cell, cell.getSpecificState());
            }
        });
    }*/

    public ForestBuilder setAt(int row, int col, VegetalState state) {
        this.f.setCell(row, col, state);
        return this;
    }

    public ForestBuilder setAt(int row, int col, VegetalState state, SpecificState specificState) {
        this.f.setCell(row, col, state, specificState);
        return this;
    }

    public Cell getAt(int row, int col) {
        return this.f.getCell(row, col);
    }

    public Forest get() {
        f.calcNeighours();
        return this.f;
    }
}
