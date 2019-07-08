package fr.crosf32.fxtest.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IndexController {

    @FXML
    private Label test;

    @FXML
    private Label timer;

    @FXML
    private Label gamePlayed;

    @FXML
    private Label gameWon;

    @FXML
    private GridPane gridPane;

    private int count;

    public void setTest(String test) {
        this.test.setText(test);
    }

    public void setGamePlayed(int i) {
        this.gamePlayed.setText(String.valueOf(i));
    }

    public void setGameWon(int i) {
        this.gameWon.setText(String.valueOf(i));
    }

    public void set(int row, int col, int str) {
        Optional<Node> l = getNodeFromGridPane(gridPane, row, col);
        if(l.isPresent()) {
            ((TextField) l.get()).setText(String.valueOf(str));
        }
    }

    public void loadGrid() {
    }

    public void commitHoverEntered(MouseEvent actionEvent) {
        ((Button) actionEvent.getSource()).setStyle("-fx-background-color: #00bc60");
    }

    public void commitHoverLeft(MouseEvent actionEvent) {
        ((Button) actionEvent.getSource()).setStyle("-fx-background-color: #00E676");
    }

    public void commitClick(MouseEvent actionEvent) {
        ((Button) actionEvent.getSource()).setStyle("-fx-background-color: #00ff85");
    }

    private Optional<Node> getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == col)
                return Optional.of(node);
        return Optional.empty();
    }

    private String getFormattedTime(int secs) {
        if (secs < 60)
            return "00:00:" + (secs < 10 ? "0" : "") + secs;
        else {
            int mins = secs / 60;
            int remainderSecs = secs - (mins * 60);
            if (mins < 60) {
                return "00:" + (mins < 10 ? "0" : "") + mins + ":"
                        + (remainderSecs < 10 ? "0" : "") + remainderSecs;
            }
            else {
                int hours = mins / 60;
                int remainderMins = mins - (hours * 60);
                return (hours < 10 ? "0" : "") + hours + ":"
                        + (remainderMins < 10 ? "0" : "") + remainderMins
                        + (remainderSecs < 10 ? "0" : "") + remainderSecs;
            }
        }
    }
}
