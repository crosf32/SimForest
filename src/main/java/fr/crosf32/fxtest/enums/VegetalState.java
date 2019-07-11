package fr.crosf32.fxtest.enums;

public enum VegetalState {
    EMPTY("0", 0, 0, "#975120 "),
    YOUNG("1", 25, 75, "#06FF15"),
    SHRUB("2", 50, 50, "#01AE0C"),
    BEFORE_TREE("2", 50, 50, "#01AE0C"),
    TREE("4", 75, 25, "#025207");

    private String displayNumber, color;
    private int riskOfFire, riskOfInfection;

    VegetalState(String displayNumber, int riskOfFire, int riskOfInfection, String color) {
        this.displayNumber = displayNumber;
        this.riskOfFire = riskOfFire;
        this.riskOfInfection = riskOfInfection;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    // TODO : Add percentages
}
