package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Propagable {

    Cell[] getNeighbours(Cell c); // TODO : Check utility

    void propagate(Cell c);

    default int getNumberOfNeighboursWith(Cell[] neighbours, VegetalState state) {
        return Stream.of(neighbours).filter((cell) -> cell.getState() == state).collect(Collectors.toList()).size();
    }

    default int getNumberOfNeighboursWith(Cell[] neighbours, SpecificState state) {
        return Stream.of(neighbours).filter((cell) -> cell.getSpecificState() == state).collect(Collectors.toList()).size();
    }
}
