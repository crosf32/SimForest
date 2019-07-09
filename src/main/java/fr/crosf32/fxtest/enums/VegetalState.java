package fr.crosf32.fxtest.enums;

public enum VegetalState {
    EMPTY("0", 0, 0),
    YOUNG("1", 25, 75),
    SHRUB("2", 50, 50),
    BEFORE_TREE("2", 50, 50),
    TREE("4", 75, 25);

    private String displayNumber;
    private int riskOfFire, riskOfInfection;

    VegetalState(String displayNumber, int riskOfFire, int riskOfInfection) {
        this.displayNumber = displayNumber;
        this.riskOfFire = riskOfFire;
        this.riskOfInfection = riskOfInfection;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public int getRiskOfFire() {
        return riskOfFire;
    }

    public int getRiskOfInfection() {
        return riskOfInfection;
    }
    // TODO : Add percentages
}
