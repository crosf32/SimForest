package fr.crosf32.fxtest.enums;

public enum SpecificState {
    NONE("0"),
    FIRE("F"),
    ASH("C"),
    INFECTED("T");

    private String displayNumber;

    SpecificState(String displayNumber) {
        this.displayNumber = displayNumber;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }
}
