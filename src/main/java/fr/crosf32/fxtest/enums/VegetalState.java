package fr.crosf32.fxtest.enums;

public enum VegetalState {
    EMPTY("0"),
    YOUNG("1"),
    SHRUB("2"),
    BEFORETREE("3"),
    TREE("4");

    private String displayNumber;

    VegetalState(String displayNumber) {
        this.displayNumber = displayNumber;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }
    // TODO : Add percentages
}
