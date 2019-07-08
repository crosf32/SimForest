package fr.crosf32.fxtest.entity;

public class Grid {
    private Cell[][] cells;
    private int width, height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        generateBySize(width, height);
    }


    Cell getCell(int row, int col) {
        return cells[row][col];
    }

    void setCell(int row, int col, Cell c) {
        this.cells[row][col] = c;
    }

    private void generateBySize(int width, int height) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                setCell(x, y, new Cell());
            }
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
}
