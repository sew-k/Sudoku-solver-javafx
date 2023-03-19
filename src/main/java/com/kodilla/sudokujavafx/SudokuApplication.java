package com.kodilla.sudokujavafx;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.logic.GameProcessor;
import com.kodilla.sudokujavafx.presentation.Drawer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {
    //private static GameProcessor gameProcessor;
    @Override
    public void start(Stage stage) {
        //GameProcessor gameProcessor = new GameProcessor();
        GameProcessor gameProcessor = GameProcessor.INSTANCE;
        gameProcessor.processGame(stage);
    }

    public static void main(String[] args) {
        System.out.println(new SudokuBoard());
        launch();
    }
}