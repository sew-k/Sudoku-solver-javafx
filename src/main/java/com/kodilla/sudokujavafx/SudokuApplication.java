package com.kodilla.sudokujavafx;

import com.kodilla.sudokujavafx.logic.GameProcessor;
import javafx.application.Application;
import javafx.stage.Stage;

public class SudokuApplication extends Application {

    @Override
    public void start(Stage stage) {

        GameProcessor gameProcessor = GameProcessor.INSTANCE;
        gameProcessor.processGame(stage);
    }

    public static void main(String[] args) {

        launch();
    }
}