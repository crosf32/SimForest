package fr.crosf32.fxtest.controllers;

import fr.crosf32.fxtest.sudoku.Sudoku;
import fr.crosf32.fxtest.sudoku.grid.Cell;
import fr.crosf32.fxtest.sudoku.grid.Grid;
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

    private Sudoku sudoku;

    private int count;

    public IndexController() {
        this.sudoku = new Sudoku();
    }

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
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
        double height = 44;

        System.out.println(gridPane.getRowConstraints());
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                TextField label = new TextField();
                label.setPrefHeight(height);
                label.setMaxHeight(height);
                label.setMinHeight(height);

                label.setAlignment(Pos.CENTER);
                label.setId("field-"+(r*9+c));
                label.setStyle("-fx-background-color: rgba(255, 255, 255, 0); -fx-font-size: 20px");

                if(r == 2 || r == 5) {
                    label.setBorder(new Border(new BorderStroke(Color.RED, Color.BLACK, Color.RED, Color.RED,
                            BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                            CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
                }
                if(c == 3 || c == 6) {
                    if(r == 2 || r == 5) {
                        label.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.RED, Color.RED,
                                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                                CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
                    } else {
                        label.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.RED, Color.RED,
                                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                                CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
                    }
                }

                label.textProperty().addListener((obs, oldText, newText) -> {
                    if(!newText.matches("^([1-9]|\\d)$") && !newText.equalsIgnoreCase("")) {
                        label.setText(oldText);
                        label.setStyle("-fx-background-color: #df403a; -fx-font-size: 20px");
                        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                        Runnable task = () -> {
                           label.setStyle("-fx-background-color: rgba(255, 255, 255, 0); -fx-font-size: 20px");
                        };
                        executor.schedule(task, 1, TimeUnit.SECONDS);
                    }

                    if(sudoku.isGrid()) {
                        try {
                            int row = GridPane.getRowIndex(label);
                            int col = GridPane.getColumnIndex(label);
                            if(newText.equalsIgnoreCase(String.valueOf(sudoku.getGrid().get().get().getCellAt(col, row).getValue()))) {
                                System.out.println("Oue");
                            } else {
                                System.out.println("Non");
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                GridPane.setHalignment(label, HPos.CENTER);

                GridPane.setConstraints(label, r, c);
                gridPane.getChildren().addAll(label);
            }
        }

        sudoku.getGrid().thenAccept((Optional<Grid> opt) -> {
           if(opt.isPresent()) {
               for(Cell c : opt.get().getCells()) {
                   if(c.getValue() != 0) {
                       set(c.getRow(), c.getColumn(), c.getValue());
                   }
               }
               Timeline time = new Timeline();
               time.setCycleCount(Timeline.INDEFINITE);
               KeyFrame frame = new KeyFrame(Duration.seconds(1), (actionEvent -> {
                   count++;
                   timer.setText(getFormattedTime(count));
               }));
               time.getKeyFrames().add(frame);
               time.playFromStart();
           }
        });
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

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
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
