package fr.crosf32.fxtest.controllers;

import fr.crosf32.fxtest.FxTest;
import fr.crosf32.fxtest.scenes.frontend.Home;
import fr.crosf32.fxtest.storage.database.DatabaseHandler;
import fr.crosf32.fxtest.entity.User;
import fr.crosf32.fxtest.security.HashHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController {
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Label connectionMessage;

    private DatabaseHandler databaseHandler;

    public LoginController() {
        databaseHandler = FxTest.getDatabaseHandler();
    }


    @FXML
    public void connect(ActionEvent actionEvent) {
        try {
            Optional<User> user = databaseHandler.getUser(textUsername.getText()).get();
            if(user.isPresent()) {
                databaseHandler.addGamePlayed(user.get().getUserName(), user.get().getGamePlayed()+1);
                HashHandler hashHandler = new HashHandler();
                System.out.println(user.get().getPassword());
                if(hashHandler.is(user.get().getPassword(), textPassword.getText())) {
                    connectionMessage.setTextFill(Color.GREEN);
                    connectionMessage.setText("Vous êtes bien connecté");
                    Stage stageTheLabelBelongs = (Stage) connectionMessage.getScene().getWindow();
                    System.out.println(stageTheLabelBelongs);
                    stageTheLabelBelongs.setScene(new Home().getScene(user.get()));
                } else {
                    connectionMessage.setTextFill(Color.RED);
                    connectionMessage.setText("Le nom d'utilisateur ou le mot de passe est erroné");
                }
            } else {
                connectionMessage.setTextFill(Color.RED);
                connectionMessage.setText("Le nom d'utilisateur ou le mot de passe est erroné");
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
