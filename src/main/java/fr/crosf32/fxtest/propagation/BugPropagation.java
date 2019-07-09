package fr.crosf32.fxtest.propagation;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class BugPropagation implements Propagable {

    private Forest forest;

    public BugPropagation(Forest forest) {
        this.forest = forest;
    }

    @Override
    public void propagate(Vegetal c) {
        Set<Vegetal> neighbours = getDirectNeighbours(c);

        int infectedNeighbours = getNumberOfNeighboursWith(neighbours, SpecificState.INFECTED);

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

    private Set<Vegetal> getDirectNeighbours(Vegetal parent) {
        return parent.getNeighbours().stream().filter(vegetal -> vegetal.getRow() == parent.getRow() || vegetal.getCol() == parent.getCol()).collect(Collectors.toSet());
    }
}
