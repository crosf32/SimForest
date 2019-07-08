package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;

public class BugPropagation implements Propagable {
    @Override
    public Cell[] getNeighbours(Cell c) {
        return new Cell[0];
    }

    @Override
    public void propagate(Cell c) {
        int infectedNeighbours = getNumberOfNeighboursWith(getNeighbours(c), SpecificState.INFECTED);

        if(c.getSpecificState() == SpecificState.INFECTED) {
            c.setSpecificState(SpecificState.NONE);
            c.setState(VegetalState.EMPTY);
        } else {
            if(infectedNeighbours >= 1 && c.getSpecificState() == SpecificState.NONE) {
                int probability = c.getState().getRiskOfInfection();

                int rand = new Random().nextInt(100);

                if(rand <= probability) {
                    c.setSpecificState(SpecificState.INFECTED);
                }
            }
        }
    }
}
