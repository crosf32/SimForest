package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Set;
import java.util.stream.Collectors;

public class GrowingPropagation implements Propagable {

    @Override
    public void propagate(Vegetal c) {
        Set<Vegetal> neighbours = c.getNeighbours();

        int treesNeighbours = getNumberOfNeighboursWith(neighbours, VegetalState.TREE);
        int shrubNeighbours = getNumberOfShrubs(neighbours);

        if(c.getState().getRiskOfFire() != -1) {
            switch (c.getState()) {
                case EMPTY:
                    if(treesNeighbours >= 2 || shrubNeighbours >= 3 || (treesNeighbours == 1 && shrubNeighbours == 2)) {
                        c.setState(VegetalState.YOUNG);
                    }
                    break;
                case YOUNG:
                    int shrubOrTreeNeighbours = getNumberOShrubOrTreeNeighbours(neighbours);

                    if(shrubOrTreeNeighbours <= 3) {
                        c.setState(VegetalState.SHRUB);
                    }
                    break;
                case SHRUB:
                    c.setState(VegetalState.BEFORE_TREE);
                    break;

                case BEFORE_TREE:
                    c.setState(VegetalState.TREE);
                default:

                    break;
            }
        }
    }

    private int getNumberOfShrubs(Set<Vegetal> neighbours) {
        return neighbours.stream().filter((cell) -> ((cell.getLastState() == VegetalState.SHRUB || cell.getLastState() == VegetalState.BEFORE_TREE))).collect(Collectors.toList()).size();
    }

    private int getNumberOShrubOrTreeNeighbours(Set<Vegetal> neighbours) {
        return neighbours.stream().filter((cell) -> ((cell.getLastState() == VegetalState.SHRUB || cell.getLastState() == VegetalState.TREE))).collect(Collectors.toList()).size();
    }
}
