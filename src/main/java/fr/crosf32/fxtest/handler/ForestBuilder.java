package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.entity.Forest;
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

        int nbr = (width*height) / 5;

        int fire = 0;
        int infected = 0;

        int infectedMax = (width*height)/100;

        Integer[][] randomCells = new Integer[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                randomCells[x][y] = -1;
            }
        }

        for(int i = 0; i < nbr; i++) {
            int randomRow = new Random().nextInt(width);
            int randomCol = new Random().nextInt(height);

            if(randomCells[randomRow][randomCol] == -1) {
                VegetalState randomState = VegetalState.values()[new Random().nextInt(5)];
                if(fire <= 5) {
                    randomState = VegetalState.FIRE;
                    fire++;
                } else if(infected <= infectedMax) {
                    randomState = VegetalState.INFECTED;
                    infected++;
                }

                setAt(randomRow, randomCol, randomState);

                randomCells[randomRow][randomCol] = 1;
            }
        }
        return this;
    }

    public void reset() {
        f.resetGrid();
    }

    public ForestBuilder setAt(int row, int col, VegetalState state) {
        this.f.setCell(row, col, state);
        return this;
    }

    public Forest get() {
        f.calcNeighours();
        return this.f;
    }
}
