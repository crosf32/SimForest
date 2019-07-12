package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;
import java.util.Set;

public class FirePropagation implements Propagable {

    @Override
    public void propagate(Vegetal c) {
        Set<Vegetal> neighbours = c.getNeighbours();

        int probability = c.getState().getRiskOfFire();
        int firedNeighbours = getNumberOfNeighboursWith(neighbours, VegetalState.FIRE);

        if(firedNeighbours >= 1 && c.getState().getRiskOfFire() != -1) { // pas un specific && fired >= 1
           int rand = new Random().nextInt(100);

           if(rand <= probability) {
               c.setState(VegetalState.FIRE);
           }
        } else {
            switch(c.getState()) {
                case FIRE:
                    c.setState(VegetalState.ASH);
                    break;
                case ASH:
                    c.setState(VegetalState.EMPTY);
                    break;
            }
        }
    }
}
