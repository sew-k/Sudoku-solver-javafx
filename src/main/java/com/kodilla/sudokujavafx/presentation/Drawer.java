package com.kodilla.sudokujavafx.presentation;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuMove;
import com.kodilla.sudokujavafx.logic.GameDifficulty;
import com.kodilla.sudokujavafx.logic.GameProcessor;
import com.kodilla.sudokujavafx.logic.Validator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public enum Drawer {
    INSTANCE;

    private Drawer() {

    }

    public Pane drawBoardInPane(Stage stage, SudokuBoard board) {
        GridPane gridPane = new GridPane();
        for (int r = 0; r < board.getSudokuBoardList().size(); r++) {
            for (int c = 0; c < board.getSudokuBoardList().get(r).getSudokuElementsList().size(); c++) {
                TextField field = drawField(stage, board.getSudokuBoardList().get(r).getSudokuElementsList().get(c));
                gridPane.add(field, c, r);
            }
        }
        return gridPane;
    }

    public TextField drawField(Stage stage, SudokuElement sudokuElement) {
        TextField field = setupSudokuElementTextField(sudokuElement);
        field = addFieldValueProcessor(stage, sudokuElement, field);
        field.setFocusTraversable(false);
        return field;
    }

    //TODO - better rebuild this method needed
    private TextField addFieldValueProcessor(Stage stage, SudokuElement sudokuElement, TextField field) {
        field.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                if ((Validator.INSTANCE.checkFieldTextToInt(newValue)) && !newValue.equals("0")) {
                    GameProcessor.INSTANCE.getBoard().calculateBoard();
                    if (GameProcessor.getDifficulty().equals(GameDifficulty.EASY)) {
                        if (!(sudokuElement.getAvailableFieldValues(GameProcessor.INSTANCE.getBoard())
                                .contains(Integer.parseInt(newValue)))) {
                            field.setStyle("-fx-text-fill: RED; -fx-border-color: gray; -fx-font-size: 18");
                        } else {
                            field.setStyle("-fx-text-fill: BLUE; -fx-border-color: gray; -fx-font-size: 18");
                        }
                    } else if (GameProcessor.getDifficulty().equals(GameDifficulty.MEDIUM)) {
                        field.setStyle("-fx-text-fill: grey; -fx-border-color: gray; -fx-font-size: 18");
                    }
                    SudokuBoard copyBoardToBackTrack;
                    copyBoardToBackTrack = GameProcessor.INSTANCE.getCopyOfBoard(GameProcessor.INSTANCE.getBoard());
                    sudokuElement.setFieldValue(Integer.parseInt(newValue));

                    if (copyBoardToBackTrack != null) {
                        GameProcessor.INSTANCE.getBackTrack()
                                .add(new SudokuMove(copyBoardToBackTrack,
                                        sudokuElement.getRowIndex(),
                                        sudokuElement.getColIndex(),
                                        sudokuElement.getFieldValue()));
                    }

                    GameProcessor.INSTANCE.getBoard().calculateBoard();
                    drawMainWindow(stage,GameProcessor.INSTANCE.getBoard());
                } else {
                    field.setText("");
                }
            } catch (NumberFormatException e) {
                System.out.println("exception: " + e);
                field.clear();
                sudokuElement.setFieldValue(0);
            }
        }
        ));
        return field;
    }

    private String getFieldValueFromSudokuElement(SudokuElement sudokuElement) {
        String fieldValue = Integer.toString(sudokuElement.getFieldValue());
        if (fieldValue.equals("0")) fieldValue = "";
        return fieldValue;
    }

    private TextField setupSudokuElementTextField(SudokuElement sudokuElement) {
        TextField field = new TextField(getFieldValueFromSudokuElement(sudokuElement));
        field.setMinSize(50,50);
        field.setMaxSize(50,50);
        field.setAlignment(Pos.CENTER);
        if (sudokuElement.isFixed()) {
            field.setStyle(ViewConfig.FIXED_SUDOKU_ELEMENT);
            field.setEditable(false);
        } else if (!sudokuElement.isFixed() && sudokuElement.isElementValueCorrect()) {
            field.setStyle(ViewConfig.NOT_FIXED_CORRECT_SUDOKU_ELEMENT);
            field.setEditable(true);
        } else if (!sudokuElement.isFixed() && !sudokuElement.isElementValueCorrect()) {
            field.setStyle(ViewConfig.NOT_FIXED_INCORRECT_SUDOKU_ELEMENT);
            field.setEditable(true);
        }
        return field;
    }

    public void drawMainWindow(Stage stage, SudokuBoard board) {
        stage.setTitle("Sudoku game");
        MenuBar menuBar = new MenuBar();
        Menu game = new Menu("Game");
        MenuItem newGame = new MenuItem("New game");
        newGame.setDisable(true);
        MenuItem loadGame = new MenuItem("Load game");
        loadGame.setDisable(true);
        MenuItem saveGame = new MenuItem("Save game");
        saveGame.setDisable(true);
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
        newRandomBoard.setDisable(true);
        MenuItem setBoardManually = new MenuItem("Set board manually");
        setBoardManually.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.setBoardManually();
                GameProcessor.INSTANCE.setBoard(board);
                GameProcessor.INSTANCE.getBackTrack().clear();
                GameProcessor.INSTANCE.setOriginalBoard(GameProcessor.INSTANCE.getCopyOfBoard(board));
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        MenuItem loadBoard = new MenuItem("Load board");
        loadBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load board");
                String path = Paths.get("./src/main/resources/").toAbsolutePath().toString();
                fileChooser.setInitialDirectory(new File(path));
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    SudokuBoard newBoard = board.loadBoard(file);
                    GameProcessor.INSTANCE.getBackTrack().clear();
                    GameProcessor.INSTANCE.setBoard(newBoard);
                    GameProcessor.INSTANCE.setOriginalBoard(GameProcessor.INSTANCE.getCopyOfBoard(newBoard));
                    drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
                }
            }
        });
        MenuItem saveBoard = new MenuItem("Save board");
        saveBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save board");
                String path = Paths.get("./src/main/resources/").toAbsolutePath().toString();
                fileChooser.setInitialDirectory(new File(path));
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
        Menu difSettings = new Menu("Difficulty settings");
        RadioMenuItem easy = new RadioMenuItem("easy");
        RadioMenuItem medium = new RadioMenuItem("medium");
        RadioMenuItem hard = new RadioMenuItem("hard");
        hard.setDisable(true);
        difSettings.getItems().addAll(easy,medium,hard);
        difSettings.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                switch (GameProcessor.getDifficulty()) {
                    case EASY -> {
                        easy.setSelected(true);
                        medium.setSelected(false);
                        hard.setSelected(false);
                    }
                    case MEDIUM -> {
                        easy.setSelected(false);
                        medium.setSelected(true);
                        hard.setSelected(false);
                    }
                    case HARD -> {
                        easy.setSelected(false);
                        medium.setSelected(false);
                        hard.setSelected(true);
                    }
        }
            }
        });
        easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.setDifficulty(GameDifficulty.EASY);
                System.out.println("Difficulty setting changed to: " + GameProcessor.getDifficulty());
            }
        });
        medium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.setDifficulty(GameDifficulty.MEDIUM);
                System.out.println("Difficulty setting changed to: " + GameProcessor.getDifficulty());
            }
        });
        hard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.setDifficulty(GameDifficulty.HARD);
                System.out.println("Difficulty setting changed to: " + GameProcessor.getDifficulty());
            }
        });

        MenuItem boardSettings = new MenuItem("Board settings");
        boardSettings.setDisable(true);
        settings.getItems().addAll(difSettings, boardSettings);

        menuBar.getMenus().addAll(game, boardMenu, settings);

        String fileName = GameProcessor.INSTANCE.getBoard().getName();

        String message = Integer.toString(board.getNumberOfSolutions());
        if (GameProcessor.INSTANCE.getBoard().isBoardSolved()) message = " solved! ";

        Label topLabel = new Label("Board name: '" + fileName + "'.  Minimum possible moves: " + message);
        VBox root = new VBox();

        Button restartButton = new Button("<<");
        restartButton.setMinSize(50,50);
        restartButton.setMaxSize(50,50);
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.restartBoard();
                System.out.println(GameProcessor.INSTANCE.getBoard());
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        restartButton.setTooltip(new Tooltip("Restart this board"));
        Button undoButton = new Button("<");
        undoButton.setMinSize(50,50);
        undoButton.setMaxSize(50,50);
        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.setPreviousBoard();
                System.out.println(GameProcessor.INSTANCE.getBoard());
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        undoButton.setTooltip(new Tooltip("Undo last move"));
        Button solveOneFieldButton = new Button(">");
        solveOneFieldButton.setMinSize(50,50);
        solveOneFieldButton.setMaxSize(50,50);
        solveOneFieldButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.solveRandomSudokuElement(board);
                System.out.println(GameProcessor.INSTANCE.getBoard());
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        solveOneFieldButton.setTooltip(new Tooltip("Solve one random\n field from this\n board by CPU"));
        Button solveButton = new Button(">>");
        solveButton.setMinSize(50,50);
        solveButton.setMaxSize(50,50);
        solveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SudokuBoard solvedBoard = GameProcessor.INSTANCE.solveBoard(board);
                if (solvedBoard != null) {
                    GameProcessor.INSTANCE.setBoard(solvedBoard);
                    System.out.println(GameProcessor.INSTANCE.getBoard());
                    drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
                } else {
                    alertCantSolveBoard();
                }
            }
        });
        solveButton.setTooltip(new Tooltip("Solve this board by CPU"));
        Button clearButton = new Button("X");
        clearButton.setMinSize(50,50);
        clearButton.setMaxSize(50,50);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameProcessor.INSTANCE.setBoard(board.clearBoard());
                System.out.println(GameProcessor.INSTANCE.getBoard());
                drawMainWindow(stage, GameProcessor.INSTANCE.getBoard());
            }
        });
        clearButton.setTooltip(new Tooltip("Clear this board"));
        Button randomBoardButton = new Button("?");
        randomBoardButton.setDisable(true);
        randomBoardButton.setMinSize(50,50);
        randomBoardButton.setMaxSize(50,50);
        randomBoardButton.setTooltip(new Tooltip("Clear and set\n new random board\n based on difficulty settings"));
        HBox buttonsHBox = new HBox(restartButton, undoButton, solveOneFieldButton, solveButton, clearButton, randomBoardButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        root.getChildren().add(menuBar);
        root.getChildren().add(topLabel);
        root.getChildren().add(drawBoardInPane(stage, board));
        root.getChildren().add(buttonsHBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void alertCantSolveBoard() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Sudoku board is invalid\nUnable to solve!");
        a.setTitle("Sudoku game");
        a.setHeight(100);
        a.show();
    }

}
