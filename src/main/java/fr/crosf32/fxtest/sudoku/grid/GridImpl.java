package fr.crosf32.fxtest.sudoku.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridImpl implements Grid {

    private List<Cell> cells;

    public GridImpl() {
        this.cells = new ArrayList<>();
    }

    @Override
    public void addCell(Cell c) {
        this.cells.add(c);
    }

    @Override
    public Cell getCellAt(int row, int col) {
        return cells.stream()
                .filter(cell -> cell.getColumn() == col && cell.getRow() == row)
                .findAny()
                .get();
    }

    @Override
    public List<Cell> getColumn(int col) {
        return getColumnStream(col).collect(Collectors.toList());
    }

    @Override
    public List<Cell> getRow(int row) {
        return getRowStream(row).collect(Collectors.toList());
    }

    @Override
    public List<Cell> getSubGridCells(Cell c) {
        return getSubgGridStream(c).collect(Collectors.toList());
    }

    @Override
    public void displayGrid() {
        for(int i = 0; i < 9; i++) {
            if(i == 3 || i == 6) {
                System.out.println("---------------------");
            }
            List<Integer> row = getGuessRowInts(i);
            StringBuilder s = new StringBuilder();
            int i2 = 1;
            for(int y : row) {
                if(i2 == 3 || i2 == 6) {
                    s.append(y).append(" | ");
                } else {
                    s.append(y).append(" ");
                }
                i2++;
            }
            System.out.println(s);
        }
    }

    @Override
    public List<Cell> getCells() {
        return cells;
    }

    public List<Integer> getGuessSubGridInts(Cell c) {
        return getSubGridCells(c).stream()
                .map(cell -> (cell.getValue() == 0) ? cell.getGuessValue() : cell.getValue())
                .collect(Collectors.toList());
    }

    public List<Integer> getGuessRowInts(int row) {
        return getRowStream(row)
                .map(cell -> (cell.getValue() == 0) ? cell.getGuessValue() : cell.getValue())
                .collect(Collectors.toList());
    }

    public List<Integer> getGuessColumnInts(int col) {
        return getColumnStream(col)
                .map(cell -> (cell.getValue() == 0) ? cell.getGuessValue() : cell.getValue())
                .collect(Collectors.toList());
    }

    private Stream<Cell> getRowStream(int row) {
        return cells.stream().filter(cell -> cell.getRow() == row);
    }

    private Stream<Cell> getColumnStream(int col) {
        return cells.stream().filter(cell -> cell.getColumn() == col);
    }

    private Stream<Cell> getSubgGridStream(Cell c) {
        double xSub = Math.ceil((double) (c.getRow()+1)/3.0);
        double ySub = Math.ceil((double) (c.getColumn()+1)/3.0);
        return cells.stream().filter(cell -> {
            double xCalc = Math.ceil((double) (cell.getRow()+1)/3.0);
            double yCalc = Math.ceil((double) (cell.getColumn()+1)/3.0);
            return (xSub == xCalc && ySub == yCalc);
        });
    }
}
