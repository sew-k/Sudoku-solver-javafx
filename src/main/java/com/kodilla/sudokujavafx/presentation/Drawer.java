package com.kodilla.sudokujavafx.presentation;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuRow;
import com.kodilla.sudokujavafx.logic.GameProcessor;
import com.kodilla.sudokujavafx.logic.Validator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.io.IOException;

public enum Drawer {
    INSTANCE;
    private Drawer() {

    }
    public Pane drawBoardInPane(SudokuBoard board) {
        GridPane gridPane = new GridPane();
        for (int r = 0; r < board.getSudokuBoardList().size(); r++) {
            for (int c = 0; c < board.getSudokuBoardList().get(r).getSudokuElementsList().size(); c++) {

                TextField field = drawField(board.getSudokuBoardList().get(r).getSudokuElementsList().get(c));
                gridPane.add(field, c, r);

            }
        }
        return gridPane;
    }

//    public Label drawField(SudokuElement sudokuElement) {
//        String fieldValue = Integer.toString(sudokuElement.getFieldValue());
//        if (fieldValue.equals("0")) fieldValue = " ";
//        Label label = new Label(fieldValue);
//        label.setMinSize(30,30);
//        label.setAlignment(Pos.CENTER);
//
//        if (sudokuElement.isFixed()) {
//            label.setStyle("-fx-text-fill: BLACK; -fx-border-color: gray; -fx-font-size: 18");
//        } else {
//            label.setStyle("-fx-text-fill: BLUE; -fx-border-color: gray; -fx-font-size: 18");
//        }
//        return label;
//    }

    public TextField drawField(SudokuElement sudokuElement) {
        String fieldValue = Integer.toString(sudokuElement.getFieldValue());
        if (fieldValue.equals("0")) fieldValue = "";
        TextField field = new TextField(fieldValue);
        field.setMinSize(50,50);
        field.setMaxSize(50,50);
        field.setAlignment(Pos.CENTER);

        if (sudokuElement.isFixed()) {
            field.setStyle("-fx-text-fill: BLACK; -fx-border-color: gray; -fx-font-size: 18");
            field.setEditable(false);
        } else {
            field.setStyle("-fx-text-fill: BLUE; -fx-border-color: gray; -fx-font-size: 18");
        }
        field.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                if (Validator.INSTANCE.checkFieldTextToInt(newValue)) {
                    sudokuElement.setFieldValue(Integer.parseInt(newValue));
                    System.out.println("textfield at row index " +
                            sudokuElement.getRowIndex() + "/ column index " +
                            sudokuElement.getColIndex() + "; changed from " +
                            oldValue + " to " + newValue + " : " + sudokuElement.getFieldValue());
                    System.out.println(GameProcessor.INSTANCE.getBoard());
                } else {
                    field.setText("");
                    field.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                System.out.println("exception: " + e);
                field.clear();
            }
        }
        ));
        return field;
    }

    public void drawMainWindow(Stage stage, SudokuBoard board) {
        stage.setTitle("Sudoku game");
        MenuBar menuBar = new MenuBar();
        Menu game = new Menu("Game");
        MenuItem newGame = new MenuItem("New game");
        MenuItem loadGame = new MenuItem("Load game");
        MenuItem saveGame = new MenuItem("Save game");
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.exitGame(stage);
             }
        });
        game.getItems().addAll(newGame, loadGame, saveGame, exitGame);

        Menu boardMenu = new Menu("Board");
        MenuItem newRandomBoard = new MenuItem("New random board");
        MenuItem setBoardManually = new MenuItem("Set board manually");
        MenuItem loadBoard = new MenuItem("Load board");
        loadBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load board");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    board.loadBoard(file);
                }
            }
        });
        MenuItem saveBoard = new MenuItem("Save board");
        saveBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save board");
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        board.saveBoard(file, GameProcessor.INSTANCE.getBoard());
                    } catch (IOException e) {
                        System.out.println("Exception while saving board: " + e);
                    }
                }
            }
        });
        MenuItem clearBoard = new MenuItem("Clear board");
        clearBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.setBoard(board.clearBoard());
                System.out.println(GameProcessor.INSTANCE.getBoard());
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        boardMenu.getItems().addAll(newRandomBoard, setBoardManually, loadBoard, saveBoard, clearBoard);

        Menu settings = new Menu("Settings");
        MenuItem difSettings = new MenuItem("Difficulty settings");
        MenuItem playerSettings = new MenuItem("Player settings");
        MenuItem boardSettings = new MenuItem("Board settings");
        settings.getItems().addAll(difSettings, playerSettings, boardSettings);

        menuBar.getMenus().addAll(game, boardMenu, settings);
        VBox root = new VBox();
        root.getChildren().add(menuBar);
        root.getChildren().add(drawBoardInPane(board));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
