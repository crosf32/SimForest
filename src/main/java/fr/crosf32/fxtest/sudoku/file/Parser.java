package fr.crosf32.fxtest.sudoku.file;

import fr.crosf32.fxtest.sudoku.grid.Grid;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface Parser {

    Optional<Grid> createGrid(String filePath) throws FileNotFoundException;

    default Optional<Path> getFile(String filePath) {
        Path path = Paths.get(filePath);
        return (path.toFile().isFile() ? Optional.of(path) : Optional.empty());
    }
}
