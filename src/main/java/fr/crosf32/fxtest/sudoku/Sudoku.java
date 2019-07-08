package fr.crosf32.fxtest.sudoku;

import fr.crosf32.fxtest.sudoku.file.GridParser;
import fr.crosf32.fxtest.sudoku.grid.Grid;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Sudoku {
    private Grid grid = null;

    public CompletableFuture<Optional<Grid>> getGrid() {
        CompletableFuture<Optional<Grid>> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Grid> gridReturn = Optional.ofNullable(grid);

                if (gridReturn.isEmpty()) {
                    gridReturn = new GridParser().createGrid(System.getProperty("user.dir") + "/newgrid.txt");
                    if (gridReturn.isEmpty()) {
                        System.out.println("Une erreur est survenue, veuillez vérifier votre fichier");
                    } else {
                        this.grid = gridReturn.get();
                    }
                }
                return gridReturn;
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        });

        return completableFuture;
    }

    public boolean isGrid() {
        return grid != null;
    }

}
