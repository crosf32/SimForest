package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Cell;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;

public class FirePropagation implements Propagable {

    @Override
    public Cell[] getNeighbours(Cell c) {
        return new Cell[0];
    }

    @Override
    public void propagate(Cell c) {
        int probability = c.getState().getRiskOfFire();
        int firedNeighbours = getNumberOfNeighboursWith(getNeighbours(c), SpecificState.FIRE);

        if(firedNeighbours >= 1) {
           int rand = new Random().nextInt(100);

           if(rand <= probability) {
               c.setSpecificState(SpecificState.FIRE);
           }
        } else {
            switch(c.getSpecificState()) {
                case FIRE:
                    c.setSpecificState(SpecificState.ASH);
                    break;
                case ASH:
                    c.setSpecificState(SpecificState.NONE);
                    c.setState(VegetalState.EMPTY);
                    break;
            }
        }
    }
}
