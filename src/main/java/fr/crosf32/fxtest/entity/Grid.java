package fr.crosf32.fxtest.entity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Grid<T extends Cell> {
    private T[][] cells;
    private int width, height;
    abstract Set<T> getAllNeighbours(T c);

    private Class<T> clazz;

     Grid(Class<T> clazz, int width, int height) {
        this.clazz = clazz;
        this.width = width;
        this.height = height;
        this.cells = (T[][]) Array.newInstance(clazz, width, height);
        generateBySize(width, height);
    }

    T getCell(int row, int col) {
        return cells[row][col];
    }

    void setCell(T c) {
        this.cells[c.getRow()][c.getCol()] = c;
    }

    private void generateBySize(int width, int height) {
        try {
            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    setCell(clazz.getConstructor(int.class, int.class).newInstance(x, y));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /*public Set<T> getAllNeighbours(T c) {
        Set<T> cells = new HashSet<>();

        for(int x = (c.getRow() - 1); x <= (c.getRow() + 1); x++) {
            for(int y = c.getCol()-1; y <= (c.getCol() + 1); y++) {
                if((x == c.getRow() && y == c.getCol()) || x < 0 || y < 0 || x > (width-1) || y > (height-1)) {
                    continue;
                }
                cells.add(getCell(x, y));
            }
        }

        return cells;
    }*/

    public Set<T> getCells() {
        return Stream.of(cells).flatMap(Arrays::stream).collect(Collectors.toSet());
    }
}
