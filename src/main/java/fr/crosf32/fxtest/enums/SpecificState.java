package fr.crosf32.fxtest.enums;

public enum SpecificState {
    NONE("0", ""),
    FIRE("F", "#FF0000"),
    ASH("C", "#575957"),
    INFECTED("T", "#FBFF00");

    private String displayNumber, color;

    SpecificState(String displayNumber, String color) {
        this.displayNumber = displayNumber;
        this.color = color;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public String getColor() {
        return color;
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }
}
