package fr.crosf32.fxtest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class MainController {

    @FXML
    private GridPane forest;

    public void loadGrid(int width, int height) {
        forest.getRowConstraints().clear();
        forest.getColumnConstraints().clear();

        for (int i = 0; i < width; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            forest.getRowConstraints().add(row);
        }

        for (int y = 0; y < height; y++) {
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setHgrow(Priority.ALWAYS);
            forest.getColumnConstraints().add(column1);
        }

        System.out.println("Load grid with " + width + " " + height);
    }

    public void young() {

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

    public void nextFrame() {

    }

    public void play() {

    }

    public void end() {

    }

    public void bush() {

    }

    public void tree() {

    }

    public void start() {

    }
}
