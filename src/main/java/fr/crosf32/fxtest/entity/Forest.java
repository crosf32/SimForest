package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.VegetalState;

public class Forest extends Grid {
    public Forest(int width, int height) {
        super(width, height);
    }


    public void displayForConsole() {
        String table = "";
        for(int x = 0; x < getWidth(); x++) {
            String row = "";
            for(int y = 0; y < getHeight(); y++) {
                row+= getCell(x, y).getState().getDisplayNumber() + " ";
            }
            table+=row + " \n";
        }

        System.out.println(table);
    }

    public void setCell(int row, int col, VegetalState state) {
        this.setCell(row, col, new Cell().setState(state));
    }

    public Cell getCell(int row, int col) {
        return super.getCell(row, col);
    }
}
