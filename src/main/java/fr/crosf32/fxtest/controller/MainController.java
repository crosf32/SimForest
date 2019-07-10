package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.handler.ForestWindowManager;
import fr.crosf32.fxtest.window.WindowForestUpdatable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.Set;

public class MainController implements WindowForestUpdatable {

    @FXML
    private GridPane forestGridPane;

    @FXML
    private TextField delayInput;

    @FXML
    private TextField maxTimeInput;

    private ForestWindowManager forestWindowManager;

    private Forest forest;
    private ForestBuilder forestBuilder;
    private ForestSimulator forestSimulator;

    private VegetalState chosenVegetal;

    private int width, height;

    // placement vegetal ==> 60%


    public void loadGrid(int width, int height) {
        this.width = width;
        this.height = height;

        this.forest = new Forest(width, height);
        this.forestBuilder = new ForestBuilder(forest);

        forestGridPane.getRowConstraints().clear();
        forestGridPane.getColumnConstraints().clear();

        for (int i = 0; i < width; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            forestGridPane.getRowConstraints().add(row);
        }

        for (int y = 0; y < height; y++) {
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setHgrow(Priority.ALWAYS);
            forestGridPane.getColumnConstraints().add(column1);
        }

        for (int i = 0; i < width; i++) {
            for (int y = 0; y < width; y++) {
                addPane(i, y);
            }
        }

        forestGridPane.setOnMouseClicked((event) -> {
            for( Node node: forestGridPane.getChildren()) {
                if( node instanceof Pane) {
                    if( node.getBoundsInParent().contains(event.getSceneX(),  event.getSceneY())) {
                        int row = GridPane.getRowIndex( node);
                        int col = GridPane.getColumnIndex( node);
                        getForestBuilder().setAt(row, col, chosenVegetal);
                        applyColor((Pane) node, chosenVegetal);
                    }
                }
            }
        });
    }

    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : forestGridPane.getChildren())
            if (GridPane.getColumnIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        applyColor(pane, VegetalState.EMPTY);
        forestGridPane.add(pane, colIndex, rowIndex);
    }

    public void young() {
        this.chosenVegetal = VegetalState.YOUNG;
    }

    public void bush() {
        this.chosenVegetal = VegetalState.SHRUB;
    }

    public void tree() {
        this.chosenVegetal = VegetalState.TREE;
    }

    public void startFire() {

    }

    public void startInfection() {

    }

    public void export() {
        resetGrid();
        getForestBuilder().reset();
    }

    public void importButton() {

    }

    public void nextFrame() { // TODO : Remove ?

    }

    public void play() {

    }

    public void end() {

    }

    public void start() {
        this.forest.calcNeighours();

        ForestSimulator forestSimulator = buildForestSimulator();
        if(forest != null) {
            forestSimulator.launchSimulation(this);
        }
    }


    private ForestSimulator buildForestSimulator() {
        ForestSimulator forestSimulator = new ForestSimulator(getForestBuilder().get());
        int delay = getSafeInt(delayInput.getText());
        int maxTime = getSafeInt(maxTimeInput.getText());

        System.out.println(delay);
        System.out.println(maxTime);
        if(delay < 0 || maxTime < 0) {
            System.out.println("Error inputs");
            //TODO : Message error
            return null;
        }

        return forestSimulator.setDelay(delay).setMaxTime(maxTime);
    }

    private int getSafeInt(String s) { // TODO : Duplicate code
        try {
            return Integer.valueOf(s);
        } catch(Exception e) {
            return -1;
        }
    }

    private void applyColor(Pane pane, VegetalState state) {
        applyStringColor(pane, state.getColor());
    }

    private void applyColor(Pane pane, SpecificState state) {
        applyStringColor(pane, state.getColor());
    }

    private void applyStringColor(Pane pane, String color) {
        pane.setStyle("-fx-background-color: " + color);
    }

    public void updateCell(int row, int col, VegetalState state) {
         applyColor((Pane) getNodeFromGridPane(row, col), state);
    }

    @Override
    public void updateCells(Set<Vegetal> vegetals) {
        vegetals.forEach(vegetal -> {
            int row = vegetal.getRow();
            int col = vegetal.getCol();

            Pane pane = (Pane) getNodeFromGridPane(col, row);
            System.out.println(col + " " + row);
            System.out.println(pane.getClass());
            System.out.println(pane.getStyle());
            applyColor(pane, vegetal.getState());
            System.out.println(pane.getStyle());
        });
    }

    public ForestSimulator getForestSimulator() {
        return forestSimulator;
    }

    public void setForestSimulator(ForestSimulator forestSimulator) {
        this.forestSimulator = forestSimulator;
    }

    public ForestBuilder getForestBuilder() {
        return forestBuilder;
    }

    public void setForestBuilder(ForestBuilder forestBuilder) {
        this.forestBuilder = forestBuilder;
    }

    public void resetGrid() {
        for (int i = 0; i < width; i++) {
            for (int y = 0; y < height; y++) {
                applyColor((Pane) getNodeFromGridPane(i, y), VegetalState.EMPTY);
            }
        }
    }
}
