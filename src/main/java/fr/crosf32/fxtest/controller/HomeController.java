package fr.crosf32.fxtest.controller;

import fr.crosf32.fxtest.SlimForest;
import fr.crosf32.fxtest.database.DatabaseHandler;
import fr.crosf32.fxtest.utils.IntegerUtils;
import fr.crosf32.fxtest.utils.WindowDialogUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class HomeController {

    @FXML
    private TextField nbrCol;

    @FXML
    private TextField nbrRow;

    @FXML
    private Label errorText;

    public void randomButton() {
        openMainFrame(true, true);
    }

    public void importButton() {
        Platform.runLater(() -> {
            try {
                openConnexionDialog();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                errorText.setText("Une erreur s'est produite");
            }
        });
    }

    public void generateGrid() {
        openMainFrame(false, true);
    }

    private void openMainFrame(boolean random, boolean checkInput) {
        if(checkInput && !inputAreGood()) {
            errorText.setText("Veuillez définir toutes les valeurs (entiers <= 150)");
            return;
        }

        Platform.runLater(() -> {
            errorText.setText("");

            SlimForest.getInstance().getFxWindowManager().openMainWindow(IntegerUtils.getSafeInt(nbrCol.getText()), IntegerUtils.getSafeInt(nbrRow.getText()), random);
        });

    }

    private void openConnexionDialog() {
        Dialog<List<String>> dialog = WindowDialogUtils.getConnexionDialog();

        Optional<List<String>> result = dialog.showAndWait();

        if(result.isPresent()) {
            List<String> res = result.get();
            DatabaseHandler handler = SlimForest.getInstance().loadDatabase(res.get(0), res.get(1), res.get(2));

            if(handler.getConnector().isConnected()) {
                try {
                    int configNumber = handler.getNumberOfConfigs().get();
                    if(configNumber == 0) {
                        errorText.setText("Erreur : Aucune configuration présente sur cette base de donnée");
                        return;
                    }
                    openChooseConfigNumber(configNumber);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openChooseConfigNumber(int max) {
        System.out.println("ok la");
        Dialog<String> dialog = WindowDialogUtils.getConfigNumberDialog(max);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> SlimForest.getInstance().getFxWindowManager().openMainWindowFromDatabase(IntegerUtils.getSafeInt(s)));
    }

    private boolean inputAreGood() {
        if(nbrCol.getText().length() == 0 || nbrRow.getText().length() == 0) {
            nbrCol.setText("100");
            nbrRow.setText("100");
        }

        if(!isInteger(nbrCol.getText()) || !isInteger(nbrRow.getText()) ) {
          return false;
        } else {
            int rowInt = IntegerUtils.getSafeInt(nbrCol.getText());
            int colInt = IntegerUtils.getSafeInt(nbrRow.getText());
            if(rowInt < 3 || colInt < 3 || rowInt > 250 || colInt > 250) {
                return false;
            }
        }

        return true;
    }

    private boolean isInteger(String s) {
        return IntegerUtils.getSafeInt(s) != -1;
    }
}
