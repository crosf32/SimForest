package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class BugPropagation implements Propagable {

    @Override
    public void propagate(Vegetal c) {
        Set<Vegetal> neighbours = getDirectNeighbours(c);

        int infectedNeighbours = getNumberOfNeighboursWith(neighbours, VegetalState.INFECTED);

        if(c.getState() == VegetalState.INFECTED) {
            c.setState(VegetalState.EMPTY);
        } else {
            if(infectedNeighbours >= 1 && c.getState().getRiskOfFire() != -1) {
                int probability = c.getLastState().getRiskOfInfection();

                int rand = new Random().nextInt(100);

                if(rand <= probability) {
                    c.setState(VegetalState.INFECTED);
                }
            }
        }
    }

    private Set<Vegetal> getDirectNeighbours(Vegetal parent) {
        return parent.getNeighbours().stream().filter(vegetal -> vegetal.getRow() == parent.getRow() || vegetal.getCol() == parent.getCol()).collect(Collectors.toSet());
    }
}
