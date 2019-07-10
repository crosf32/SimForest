package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.window.WindowForestUpdatable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController implements WindowForestUpdatable {

    @FXML
    private GridPane forestGridPane;

    @FXML
    private TextField delayInput;

    @FXML
    private TextField maxTimeInput;

    @FXML
    private Label error;

    @FXML
    private Label frameCount;

    private int width, height;

    private Forest forest;
    private ForestBuilder forestBuilder;
    private ForestSimulator forestSimulator;

    private VegetalState chosenVegetal;
    private SpecificState chosenSpecificState;

    private ForestSimulator currentSimulation;

    private int frameCounter = 0;

    public void nextStep() {
        ForestSimulator forestSimulator = buildForestSimulator();

        if(forestSimulator != null && frameCounter < getSafeInt(maxTimeInput.getText())) {
            forestSimulator.setDelay(1).setMaxTime(1);
            forestSimulator.launchSimulation(this);
            currentSimulation = forestSimulator;
        }
    }

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
                        if((chosenVegetal == null || chosenVegetal == VegetalState.EMPTY) && chosenSpecificState != null) {
                            getForestBuilder().setAt(row, col, VegetalState.TREE, chosenSpecificState);
                            applyColor((Pane) node, chosenSpecificState);
                        } else {
                            getForestBuilder().setAt(row, col, chosenVegetal);
                            applyColor((Pane) node, chosenVegetal);
                        }
                    }
                }
            }
        });
    }

    public void chooseYoung() {
        this.chosenVegetal = VegetalState.YOUNG;
    }

    public void chooseShrub() {
        this.chosenVegetal = VegetalState.SHRUB;
    }

    public void chooseTree() {
        this.chosenVegetal = VegetalState.TREE;
    }

    public void chooseFire() {
        this.chosenVegetal = VegetalState.EMPTY;
        this.chosenSpecificState = SpecificState.FIRE;
    }

    public void chooseInfect() {
        this.chosenVegetal = VegetalState.EMPTY;
        this.chosenSpecificState = SpecificState.INFECTED;
    }

    public void reset() {
        frameCounter = 0;
        getCurrentSimulation().setCancelled(true);
        resetGrid();
        getForestBuilder().reset();
    }

    public void export() { // TODO
        System.out.println("export");
    }

    public void save() { // TODO

    }

    public void start() {
        if(frameCounter >= getSafeInt(maxTimeInput.getText())) {
            return;
        }

        frameCounter = 0;

        ForestSimulator forestSimulator = buildForestSimulator();
        currentSimulation = forestSimulator;
        if(forestSimulator != null) {
            forestSimulator.launchSimulation(this);
            currentSimulation = forestSimulator;
        }
    }


    public void updateCell(int row, int col, VegetalState state) { // TODO : check utility
         applyColor((Pane) getNodeFromGridPane(row, col), state);
    }

    @Override
    public void updateCells(Set<Vegetal> vegetals) {
        vegetals.forEach(vegetal -> {
            int row = vegetal.getRow();
            int col = vegetal.getCol();

            Pane pane = (Pane) getNodeFromGridPane(col, row);
            if(vegetal.getSpecificState() != SpecificState.NONE) {
                System.out.println("spec " + vegetal.getSpecificState().name());
                applyColor(pane, vegetal.getSpecificState());
            } else {
                applyColor(pane, vegetal.getState());
            }
        });
        frameCounter++;
        displayFrameCount();
    }

    private void displayFrameCount() {
        Platform.runLater(() -> frameCount.setText(frameCounter + " / " + maxTimeInput.getText()));
    }

    public ForestBuilder getForestBuilder() {
        return forestBuilder;
    }

    public ForestSimulator getForestSimulator() {
        return forestSimulator;
    }

    public void setForestSimulator(ForestSimulator forestSimulator) {
        this.forestSimulator = forestSimulator;
    }

    public void setForestBuilder(ForestBuilder forestBuilder) {
        this.forestBuilder = forestBuilder;
    }

    private void resetGrid() {
        for (int i = 0; i < width; i++) {
            for (int y = 0; y < height; y++) {
                applyColor((Pane) getNodeFromGridPane(i, y), VegetalState.EMPTY);
            }
        }

        frameCount.setText("");
    }

    private ForestSimulator buildForestSimulator() {
        ForestSimulator forestSimulator = new ForestSimulator(getForestBuilder().get());
        if(frameCounter != 0) {
            forestSimulator.setTime(frameCounter);
        }

        int delay = getSafeInt(delayInput.getText());
        int maxTime = getSafeInt(maxTimeInput.getText());

        if(delay < 0 || maxTime < 0) {
            setError("Erreur : Veuillez renseignez tous les champs (entiers)");
            //TODO : Message error
            return null;
        }
        setError("");

        return forestSimulator.setDelay(delay).setMaxTime(maxTime);
    }

    private void setError(String s) {
        error.setText(s);
    }

    private int getSafeInt(String s) { // TODO : Duplicate code
        try {
            return Integer.valueOf(s);
        } catch(Exception e) {
            return -1;
        }
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

    private void applyColor(Pane pane, VegetalState state) {
        applyStringColor(pane, state.getColor());
    }

    private void applyColor(Pane pane, SpecificState state) {
        applyStringColor(pane, state.getColor());
    }

    private void applyStringColor(Pane pane, String color) {
        pane.setStyle("-fx-background-color: " + color);
    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        applyColor(pane, VegetalState.EMPTY);
        forestGridPane.add(pane, colIndex, rowIndex);
    }

    public ForestSimulator getCurrentSimulation() {
        return currentSimulation;
    }
}
