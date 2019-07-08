package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrowingPropagation implements Propagable {

    private Forest forest;

    public GrowingPropagation(Forest forest) {
        this.forest = forest;
    }

    @Override
    public Cell[] getNeighbours(Cell c) {
        return new Cell[0];
    }

    @Override
    public void propagate(Cell c) {
        Cell[] neighbours = getNeighbours(c);
        int treesNeighbours = getNumberOfNeighboursWith(neighbours, VegetalState.TREE);
        int shrubNeighbours = getNumberOfNeighboursWith(neighbours, VegetalState.SHRUB);

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

    private int getNumberOShrubOrTreeNeighbours(Cell[] neighbours) {
        return Stream.of(neighbours).filter((cell) -> (cell.getState() == VegetalState.SHRUB || cell.getState() == VegetalState.TREE)).collect(Collectors.toList()).size();
    }
}
