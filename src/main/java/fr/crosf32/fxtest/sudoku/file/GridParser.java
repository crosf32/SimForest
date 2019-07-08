package fr.crosf32.fxtest.sudoku.file;

import fr.crosf32.fxtest.sudoku.exception.GridException;
import fr.crosf32.fxtest.sudoku.grid.Cell;
import fr.crosf32.fxtest.sudoku.grid.Grid;
import fr.crosf32.fxtest.sudoku.grid.GridImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

public class GridParser implements Parser {

    @Override
    public Optional<Grid> createGrid(String filePath) {
        Optional<Path> path = getFile(filePath);

        if(path.isEmpty()) {
            System.out.println("Le fichier n'a pas été trouvé.");
        }

        Grid grid = new GridImpl();
        try {
            String sa = Files.lines(path.get())
                    .reduce((s, s2) -> s+"\n"+s2)
                    .map(s -> s = s.replaceAll("\\|", "") // remove useless characters '/'
                            .replaceAll("-", "")).get();  // remove useless characters '-'
            int row = 0;
            int colRef = 0;
            for(String line : sa.lines().collect(Collectors.toList())) {
                if(line.isEmpty()) {
                    continue;
                }
                if(colRef != 0) {
                    if(line.length() != colRef) {
                        throw new GridException("Toutes les lignes n'ont pas la même taille. Le fichier n'est pas conforme");
                    }
                } else {
                    colRef = line.length();
                }

                for(int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    Cell ce = new Cell(row, col, ((c == '.' || Character.getNumericValue(c) > 9) ? 0 : Character.getNumericValue(c)));
                    grid.addCell(ce);
                }
                row++;
            }
            return Optional.of(grid);
        } catch(Exception e) {
            System.out.println("Error : " + e.getMessage());
            return Optional.empty();
        }
    }
}
