package fr.crosf32.fxtest.utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class WindowDialogUtils {

    public static Dialog<List<String>> getConnexionDialog() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Connexion");
        dialog.setHeaderText("Se connecter à une base de donnée");

        ButtonType loginButtonType = new ButtonType("Se connecter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField database = new TextField();
        database.setPromptText("Base de donnée");

        TextField username = new TextField();
        username.setPromptText("Nom d'utilisateur");

        PasswordField password = new PasswordField();
        password.setPromptText("Mot de passe");

        grid.add(new Label("Database:"), 0, 0);
        grid.add(database, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(username, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(password, 1, 2);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            if (username.getText().length() != 0 && database.getText().length() != 0) {
                loginButton.setDisable(false);
            }
        };

        username.textProperty().addListener(changeListener);
        database.textProperty().addListener(changeListener);
        password.textProperty().addListener(changeListener);

        username.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(database::requestFocus);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return List.of(database.getText(), username.getText(), password.getText());
            }
            return null;
        });

        return dialog;
    }

    public static ChoiceDialog<String> getConfigNumberDialog(int max) {
        List<String> choices = new ArrayList<>();
        for(int i = 1; i <= max; i++) {
            choices.add("" + i);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Choisissez la configuration", choices);
        dialog.setTitle("Choix de la configuration");
        dialog.setContentText("Choisissez la configuration");

        return dialog;
    }
}
