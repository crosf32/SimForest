package fr.crosf32.fxtest.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HomePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button randomButton;

    @FXML
    private Button importButton;

    @FXML
    private Button generatButton;

    @FXML
    private TextField nbrCol;

    @FXML
    private TextField nbrRox;

    @FXML
    void generat(ActionEvent event) {

    }

    @FXML
    void importAction(ActionEvent event) {

    }

    @FXML
    void random(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert randomButton != null : "fx:id=\"randomButton\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert importButton != null : "fx:id=\"importButton\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert generatButton != null : "fx:id=\"generatButton\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert nbrCol != null : "fx:id=\"nbrCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert nbrRox != null : "fx:id=\"nbrRox\" was not injected: check your FXML file 'HomePage.fxml'.";

    }
}
