package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.SlimForest;
import fr.crosf32.fxtest.database.DatabaseHandler;
import fr.crosf32.fxtest.entity.Config;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.SpecificState;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.utils.CsvForestGenerator;
import fr.crosf32.fxtest.utils.IntegerUtils;
import fr.crosf32.fxtest.utils.WindowDialogUtils;
import fr.crosf32.fxtest.window.WindowForestUpdatable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.ExecutionException;
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
    private Config config;

    public void nextStep() {
        ForestSimulator forestSimulator = buildForestSimulator();

        if(forestSimulator != null && frameCounter < IntegerUtils.getSafeInt(maxTimeInput.getText())) {
            forestSimulator.setDelay(1).setMaxTime(1);
            forestSimulator.launchSimulation(this);
            currentSimulation = forestSimulator;
        }
    }

    public void backToMenu() {
        SlimForest.getInstance().getFxWindowManager().openMenu();
    }

    public void loadGridFromDatabase(Config config, List<Vegetal> vegetals) {
        this.config = config;

        loadGrid(config.getWidth(), config.getHeight(), false);

        for(Vegetal veg : vegetals) {
            getForestBuilder().setAt(veg.getRow(), veg.getCol(), veg.getState(), veg.getSpecificState());
            Node node = getNodeFromGridPane(veg.getRow(), veg.getCol());
            if(veg.getSpecificState() == SpecificState.NONE) {
                applyColor((Pane) node, veg.getState());
            } else {
                applyColor((Pane) node, veg.getSpecificState());
            }
        }

        delayInput.setText(String.valueOf(config.getDelay()));
        maxTimeInput.setText(String.valueOf(config.getMaxTime()));
    }

    public void loadGrid(int width, int height, boolean random) {
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
            if(chosenVegetal != null) {
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
            }
        });

        if(random) {
            Set<Vegetal> vegetals = new HashSet<>();

            Integer[][] map = forestBuilder.randomGeneration();
            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    if(map[x][y] != -1) {
                        vegetals.add(forest.getCell(x, y));
                    }
                }
            }
            updateCells(vegetals);
        }
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
        if(getCurrentSimulation() != null) {
            getCurrentSimulation().setCancelled(true);
        }
        resetGrid();
        getForestBuilder().reset();
    }

    public void export() {
        new CsvForestGenerator().generateCsv(getCurrentSimulation().getStats());
    }

    public void save() {
        try {
            openSaveDialog();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Erreeur catched");
            e.printStackTrace();
        }
    }

    private void openSaveDialog() throws ExecutionException, InterruptedException {
        DatabaseHandler databaseHandler = SlimForest.getHandler();

        if(databaseHandler != null && databaseHandler.getConnector().isConnected()) {
            openChooseConfigDialog(databaseHandler.getNumberOfConfigs().get());
        } else {
            Dialog<List<String>> dialog = WindowDialogUtils.getConnexionDialog();

            Optional<List<String>> result = dialog.showAndWait();

            if(result.isPresent()) {
                List<String> res = result.get();

                DatabaseHandler handler = SlimForest.getInstance().loadDatabase(res.get(0), res.get(1), res.get(2));

                if(handler.getConnector().isConnected()) {
                    int configNumber = handler.getNumberOfConfigs().get();
                    if(configNumber == 0) {
                        setError("Erreur : Connexion à la BDD impossible");
                        return;
                    }
                    openChooseConfigDialog(handler.getNumberOfConfigs().get());
                    System.out.println("ok");
                }
            }
        }
    }

    private void openChooseConfigDialog(int max) {
        ChoiceDialog<String> dialog = WindowDialogUtils.getConfigNumberDialog(max);

        ButtonType buttonSave = new ButtonType("Sauvegarder");
        ButtonType buttonNewConfig = new ButtonType("Créer une nouvelle config");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().setAll(buttonSave, buttonNewConfig, buttonTypeCancel);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonSave) {
                return "save";
            } else {
                return "newConfig";
            }
        });

        Optional<String> result = dialog.showAndWait();
        System.out.println(dialog.getSelectedItem());

        String res = result.get();
        if (res == "save") {
            int clicked = IntegerUtils.getSafeInt(dialog.getSelectedItem());
            if (clicked != -1) {
                SlimForest.getInstance().saveConfig(clicked, getSafeFromInput(delayInput), getSafeFromInput(maxTimeInput), width, height, forest);
                alert("La configuration a bien été sauvegardée");
                return;
            }
        }

        SlimForest.getInstance().saveNewConfig(getSafeFromInput(delayInput), getSafeFromInput(maxTimeInput), width, height, forest);
        alert("La configuration a bien été sauvegardée");
    }

    private void alert(String s) {
        setError(s);
        error.setStyle("-fx-text-fill: lightgreen;");
        Runnable r = () -> {
            Platform.runLater(() -> setError(""));
            error.setStyle("-fx-text-fill: red;");
        };

        Executors.newScheduledThreadPool(1).schedule(r, 2, TimeUnit.SECONDS);
    }

    public void start() {
        if(frameCounter >= IntegerUtils.getSafeInt(maxTimeInput.getText())) {
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

    @Override
    public void updateCells(Set<Vegetal> vegetals) {
        vegetals.forEach(vegetal -> {
            int row = vegetal.getRow();
            int col = vegetal.getCol();

            Pane pane = (Pane) getNodeFromGridPane(row, col);
            if(vegetal.getSpecificState() != SpecificState.NONE) {
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

        int delay = IntegerUtils.getSafeInt(delayInput.getText());
        int maxTime = IntegerUtils.getSafeInt(maxTimeInput.getText());

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

    private Node getNodeFromGridPane(int row, int col) {
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

    private int getSafeFromInput(TextField s) {
        int i = IntegerUtils.getSafeInt(s.getText());
        if(i == -1) return 0;
        return i;
    }

    public ForestSimulator getCurrentSimulation() {
        return currentSimulation;
    }
}
