package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Set;
import java.util.stream.Collectors;

public interface Propagable {

    void propagate(Vegetal c);

    default int getNumberOfNeighboursWith(Set<Vegetal> neighbours, VegetalState state) {
        return neighbours.stream().filter((cell) -> cell.getState() == state).collect(Collectors.toList()).size();
    }

    default int getNumberOfNeighboursWith(Set<Vegetal> neighbours, SpecificState state) {
        return neighbours.stream().filter((cell) -> cell.getSpecificState() == state).collect(Collectors.toList()).size();
    }
}
