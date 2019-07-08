package fr.crosf32.fxtest.sudoku.grid;

import java.util.List;

public interface Grid {

    void addCell(Cell c);

    Cell getCellAt(int row, int col);

    List<Cell> getColumn(int col);

    List<Cell> getRow(int row);

    List<Cell> getSubGridCells(Cell c);

    default List<Cell> getSubGridCells(int row, int col) {
        return getSubGridCells(getCellAt(row, col));
    }

    List<Cell> getCells();

    void displayGrid();

}
