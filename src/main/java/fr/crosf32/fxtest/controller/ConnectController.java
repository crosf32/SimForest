package fr.crosf32.fxtest.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConnectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button connectButton;

    @FXML
    private TextField username;

    @FXML
    private TextField passeword;

    @FXML
    void connect(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
