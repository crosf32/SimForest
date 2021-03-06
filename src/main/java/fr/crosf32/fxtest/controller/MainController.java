package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.SlimForest;
import fr.crosf32.fxtest.database.DatabaseHandler;
import fr.crosf32.fxtest.entity.Config;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;
import fr.crosf32.fxtest.handler.ForestBuilder;
import fr.crosf32.fxtest.handler.ForestSimulator;
import fr.crosf32.fxtest.utils.CsvForestGenerator;
import fr.crosf32.fxtest.utils.IntegerUtils;
import fr.crosf32.fxtest.utils.WindowDialogUtils;
import fr.crosf32.fxtest.window.WindowUpdatable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MainController implements WindowUpdatable {

    @FXML
    private GridPane forestGridPane;

    @FXML
    private Label youngNumber, shrubNumber, treeNumber, emptyNumber, fireNumber, infectNumber, error, frameCount;

    @FXML
    private TextField delayInput, maxTimeInput;

    private int width, height;

    private Forest forest;
    private ForestBuilder forestBuilder;
    private ForestSimulator forestSimulator;

    private VegetalState chosenVegetal;

    private ForestSimulator currentSimulation;

    private int frameCounter = 0;

    public void backToMenu() {
        SlimForest.getInstance().getFxWindowManager().openMenu();
    }

    public void loadGridFromDatabase(Config config, List<Vegetal> vegetals) {
        loadGrid(config.getWidth(), config.getHeight(), false);

        for(Vegetal veg : vegetals) {
            getForestBuilder().setAt(veg.getRow(), veg.getCol(), veg.getState());
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
                Vegetal veg = forest.getCell(i, y);
                addPane(i, y, veg);
                veg.paint();
            }
        }

        forestGridPane.setOnMouseClicked((event) -> {
            if(chosenVegetal != null) {
                for( Node node: forestGridPane.getChildren()) {
                    if( node instanceof Pane) {
                        if( node.getBoundsInParent().contains(event.getSceneX(),  event.getSceneY())) {
                            int row = GridPane.getRowIndex(node);
                            int col = GridPane.getColumnIndex(node);
                            getForestBuilder().setAt(row, col, chosenVegetal);
                        }
                    }
                }
            }
        });

        if(random) {
            forestBuilder.randomGeneration();
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
        this.chosenVegetal = VegetalState.FIRE;
    }

    public void chooseInfect() {
        this.chosenVegetal = VegetalState.INFECTED;
    }

    private boolean isStepByStep = false;

    public void reset() {
        resetStats();
        frameCount.setText("");
        frameCounter = 0;
        if(getCurrentSimulation() != null) {
            getCurrentSimulation().setCancelled(true);
            currentSimulation = null;
        }
        getForestBuilder().reset();
    }

    public void export() {
        if(getCurrentSimulation() != null) {
            try {
                new CsvForestGenerator().generateCsv(getCurrentSimulation().getStats());
            } catch (Exception e) {
                alert("Erreur : Veuillez fermer le fichier pour exporter de nouveau");
            }
        } else {
           alert("Erreur : Aucune simulation lancée");
        }
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

        String res = result.get();
        if (res == "save") {
            int clicked = IntegerUtils.getSafeInt(dialog.getSelectedItem());
            if (clicked != -1) {
                SlimForest.getInstance().saveConfig(clicked, getSafeFromInput(delayInput), getSafeFromInput(maxTimeInput), width, height, forest);
                success("La configuration a bien été sauvegardée");
                return;
            }
        }

        SlimForest.getInstance().saveNewConfig(getSafeFromInput(delayInput), getSafeFromInput(maxTimeInput), width, height, forest);
        success("La configuration a bien été sauvegardée");
    }

    private void success(String s) {
        shortMessage(s, true);
    }

    private void shortMessage(String s, boolean success) {
        setError(s);
        if(success) error.setStyle("-fx-text-fill: lightgreen;");
        Runnable r = () -> {
            Platform.runLater(() -> setError(""));
            error.setStyle("-fx-text-fill: red;");
        };

        Executors.newScheduledThreadPool(1).schedule(r, 2, TimeUnit.SECONDS);
    }

    private void alert(String s) {
        shortMessage(s, false);
    }


    public void nextStep() {
        ForestSimulator forestSimulator = buildForestSimulator();

        if(forestSimulator != null && ((frameCounter+1) <= getSafeFromInput(maxTimeInput) || (getCurrentSimulation() != null && getCurrentSimulation().isCancelled()))) {
            isStepByStep = true;
            forestSimulator.setDelay(1).setMaxTime(1);
            Platform.runLater(() -> forestSimulator.launchSimulation(this));
            currentSimulation = forestSimulator;
        } else {
            alert("Le programme tourne encore");
        }
    }

    public void start() {
        if(getCurrentSimulation() != null && !getCurrentSimulation().isCancelled() && !isStepByStep) {
            alert("Le programme tourne encore");
            return;
        }
        frameCounter = 0;

        ForestSimulator forestSimulator = buildForestSimulator();

        currentSimulation = forestSimulator;
        if(forestSimulator != null) {
            isStepByStep = false;
            try {
                forestSimulator.launchSimulation(this);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            currentSimulation = forestSimulator;
        }
    }

    @Override
    public void updateCells(Set<Vegetal> vegetals) {
        Platform.runLater(() -> {
            vegetals.forEach(Vegetal::paint);
            String[] stats = getCurrentSimulation().getLastStat();
            youngNumber.setText(stats[0]);
            shrubNumber.setText(stats[1]);
            treeNumber.setText(stats[2]);
            emptyNumber.setText(stats[3]);
            fireNumber.setText(stats[4]);
            infectNumber.setText(stats[5]);
        });
        frameCounter++;

        displayFrameCount();
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

    private ForestSimulator buildForestSimulator() {
        ForestSimulator forestSimulator = new ForestSimulator(getForestBuilder().get());
        List<String[]> stats = new ArrayList<>();
        if(getCurrentSimulation()!= null) {
            stats = getCurrentSimulation().getStats();
            stats.remove(0);
        }
        forestSimulator.addStats(stats);
        if(frameCounter != 0) {
            forestSimulator.setTime(frameCounter);
        }

        int delay = IntegerUtils.getSafeInt(delayInput.getText());
        int maxTime = IntegerUtils.getSafeInt(maxTimeInput.getText());

        if(delay < 0 || maxTime < 0) {
            setError("Erreur : Veuillez renseignez tous les champs (entiers)");
            return null;
        }
        setError("");

        return forestSimulator.setDelay(delay).setMaxTime(maxTime);
    }

    private void setError(String s) {
        error.setText(s);
    }

    private void addPane(int rowIndex, int colIndex, Vegetal veg) {
        forestGridPane.add(veg, colIndex, rowIndex);
    }

    private int getSafeFromInput(TextField s) {
        int i = IntegerUtils.getSafeInt(s.getText());
        if(i == -1) return 0;
        return i;
    }

    private void resetStats() {
        youngNumber.setText("");
        shrubNumber.setText("");
        treeNumber.setText("");
        emptyNumber.setText("");
        fireNumber.setText("");
        infectNumber.setText("");
    }

     private void displayFrameCount() {
        Platform.runLater(() -> {
            int max = getSafeFromInput(maxTimeInput);
            if(frameCounter > max) {
                maxTimeInput.setText(String.valueOf(frameCounter));
            }
            frameCount.setText(frameCounter + " / " + maxTimeInput.getText());
        });
    }

    public ForestSimulator getCurrentSimulation() {
        return currentSimulation;
    }
}
