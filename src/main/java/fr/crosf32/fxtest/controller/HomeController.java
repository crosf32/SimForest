package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.SlimForest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class HomeController {

    @FXML
    private TextField nbrCol;

    @FXML
    private TextField nbrRow;

    @FXML
    private Label errorText;

    public void randomButton() {

    }

    public void importButton() {

    }

    public void generateGrid() {
        if(!inputAreGood()) {
            errorText.setText("Veuillez définir toutes les valeurs (entiers <= 150)");
            return;
        }

        errorText.setText("");
        SlimForest.getInstance().getFxWindowManager().openMainWindow(getIntegerFromString(nbrCol.getText()), getIntegerFromString(nbrRow.getText()));
    }

    private boolean inputAreGood() {
        String colText = nbrCol.getText();
        String rowText = nbrRow.getText();

        if(colText.length() == 0 || rowText.length() == 0 || !isInteger(colText) || !isInteger(rowText) ) {
          return false;
        } else {
            int rowInt = getIntegerFromString(colText);
            int colInt = getIntegerFromString(rowText);
            if(rowInt < 3 || colInt < 3 || rowInt > 150 || colInt > 150) {
                return false;
            }
        }

        return true;
    }

    private boolean isInteger(String s) {
        return getIntegerFromString(s) != -1;
    }

    private int getIntegerFromString(String s) {
        try {
            return Integer.valueOf(s);
        } catch(Exception e) {
            return -1;
        }
    }
}
