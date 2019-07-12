package fr.crosf32.fxtest.entity;

import fr.crosf32.fxtest.enums.VegetalState;

import java.util.HashSet;
import java.util.Set;

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
                row += veg.getState().getDisplayNumber() + " ";
            }
            table += row + " \n";
        }

        System.out.println(table);
    }

    @Override
    Set<Vegetal> getAllNeighbours(Vegetal c) {
        Set<Vegetal> cells = new HashSet<>();

        for(int x = (c.getRow() - 1); x <= (c.getRow() + 1); x++) {
            for(int y = c.getCol()-1; y <= (c.getCol() + 1); y++) {
                if((x == c.getRow() && y == c.getCol()) || x < 0 || y < 0 || x > (getWidth()-1) || y > (getHeight()-1)) {
                    continue;
                }
                cells.add(getCell(x, y));
            }
        }

        return cells;
    }

    public void setCell(int row, int col, VegetalState state) {
        super.getCell(row, col).setState(state).setLastState(state).paint();
    }

    public void calcNeighours() {
        getCells().forEach(vegetal -> vegetal.setNeighbours(getAllNeighbours(vegetal)));
    }

    public Vegetal getCell(int row, int col) {
        if(row < getWidth() && col < getHeight()) {
            return super.getCell(row, col);
        } else return null;
    }

    public void resetGrid() {
        getCells().stream().forEach(vegetal -> {
            vegetal.setState(VegetalState.EMPTY).setLastState(VegetalState.EMPTY);
            vegetal.paint();
        });
    }
}
