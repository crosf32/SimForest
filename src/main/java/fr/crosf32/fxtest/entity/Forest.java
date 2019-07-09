package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

public class Forest extends Grid<Vegetal> {
    public Forest(int width, int height) {
        super(Vegetal.class, width, height);
    }

    public void displayForConsole() {
        String table = "";
        for(int x = 0; x < getWidth(); x++) {
            String row = "";
            for(int y = 0; y < getHeight(); y++) {
                Vegetal veg = getCell(x, y);
                if(veg.getSpecificState() != SpecificState.NONE) {
                    row += veg.getSpecificState().getDisplayNumber() + " ";
                } else {
                    row += veg.getState().getDisplayNumber() + " ";
                }

            }
            table += row + " \n";
        }

        System.out.println(table);
    }

    public void setCell(int row, int col, VegetalState state) {
        super.setCell(new Vegetal(row, col).setState(state).setLastState(state));
    }

    public void setCell(int row, int col, VegetalState state, SpecificState specificState) {
        super.setCell(new Vegetal(row, col).setState(state).setLastState(state).setSpecificState(specificState).setLastSpecificState(specificState));
    }

    public void calcNeighours() {
        getCells().forEach(vegetal -> {
            vegetal.setNeighbours(getAllNeighbours(vegetal));
        });
    }

    public Vegetal getCell(int row, int col) {
        return super.getCell(row, col);
    }
}
