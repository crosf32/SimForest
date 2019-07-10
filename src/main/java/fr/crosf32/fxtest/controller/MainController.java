package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.handler.ForestWindowManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private GridPane forestGridPane;

    private ForestWindowManager forestWindowManager;

    private Forest forest;
    private ForestBuilder forestBuilder;
    private ForestSimulator forestSimulator;

    private VegetalState chosenVegetal;

    public void loadGrid(int width, int height) {
        this.forest = new Forest(width, height);
        this.forestBuilder = new ForestBuilder(forest);
        this.forestSimulator = new ForestSimulator(forest);

        this.forestBuilder.setAt(0, 0, VegetalState.YOUNG);

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

        /*forest.setOnMouseClicked((event) -> {
            for( Node node: forest.getChildren()) {
                if( node instanceof Pane) {
                    if( node.getBoundsInParent().contains(event.getSceneX(),  event.getSceneY())) {
                        System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                        node.setStyle("-fx-background-color: red;");
                    }
                }
            }
        });*/

        forestGridPane.setOnMouseDragOver((event) -> {
            for( Node node: forestGridPane.getChildren()) {
                if( node instanceof Pane) {
                    if( node.getBoundsInParent().contains(event.getSceneX(),  event.getSceneY())) {
                        System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                        node.setStyle("-fx-background-color: red;");
                    }
                }
            }
        });
    }

    // setAt --> F
    // getCell(x, y).getState().getColor();
    // set simulation delay
    // set

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: " + VegetalState.EMPTY.getColor());
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

    public void nbrFrame() {

    }

    public void export() {

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
        this.forestSimulator.setDelay(0).setMaxTime(2);
        this.forestSimulator.launchSimulation();
        updateCells(new HashSet<>(this.forest.getCells()));
    }

    public void updateCells(Set<Vegetal> vegetals) {
        vegetals.forEach(vegetal -> {
            int row = vegetal.getRow();
            int col = vegetal.getCol();

            Pane pane = (Pane) getNodeFromGridPane(forestGridPane, row, col);
            pane.setStyle("-fx-background-color: " + vegetal.getColor());
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
}
