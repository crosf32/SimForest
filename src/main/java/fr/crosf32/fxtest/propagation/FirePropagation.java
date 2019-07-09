package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;
import java.util.Set;

public class FirePropagation implements Propagable {
    private Forest forest;

    public FirePropagation(Forest forest) {
        this.forest = forest;
    }

    @Override
    public void propagate(Vegetal c) {
        Set<Vegetal> neighbours = c.getNeighbours();

        int probability = c.getState().getRiskOfFire();
        int firedNeighbours = getNumberOfNeighboursWith(neighbours, SpecificState.FIRE);

        if(firedNeighbours >= 1 && c.getLastState() != VegetalState.EMPTY && c.getLastSpecificState() == SpecificState.NONE) {
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
