package fr.crosf32.fxtest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    private TextField nbrCol;

    @FXML
    private TextField nbrRow;

    public void randomButton() {

    }

    public void importButton() {

    }

    public void generateGrid() {
        System.out.println(nbrCol.getText());
    }
}
