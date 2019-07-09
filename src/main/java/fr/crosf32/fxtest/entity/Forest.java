package fr.crosf32.fxtest.entity;

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
                row += getCell(x, y).getState().getDisplayNumber() + " ";
            }
            table += row + " \n";
        }

        System.out.println(table);
    }

    public void setCell(int row, int col, VegetalState state) {
        super.setCell(new Vegetal(row, col).setState(state));
    }

    public void calcNeibhours() {
        getCells().forEach(vegetal -> {
            vegetal.setNeighbours(getAllNeighbours(vegetal));
        });
    }

    public Vegetal getCell(int row, int col) {
        return super.getCell(row, col);
    }
}
