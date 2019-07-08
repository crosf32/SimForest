package fr.crosf32.fxtest.entity;

public class Grid {
    private Cell[][] cells;

    public Grid(int width, int height) {
        generateBySize(width, height);
    }


    // TODO : random size generation

    private void generateBySize(int width, int height) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }
    }
}
