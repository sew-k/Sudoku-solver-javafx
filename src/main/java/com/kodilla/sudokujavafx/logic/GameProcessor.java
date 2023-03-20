package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.presentation.Drawer;
import javafx.stage.Stage;

public enum GameProcessor {
    INSTANCE;
    private Drawer drawer = Drawer.INSTANCE;
    private SudokuBoard board;
    private GameProcessor() {
    }
    public void processGame(Stage stage, SudokuBoard board) {

        drawer.drawMainWindow(stage, board);
    }
    public void processGame(Stage stage) {
        board = new SudokuBoard();
        drawer.drawMainWindow(stage, board);
    }

    public void exitGame(Stage stage) {
        stage.close();
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }
}
