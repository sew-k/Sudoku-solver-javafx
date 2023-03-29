package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.presentation.Drawer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum GameProcessor {
    INSTANCE;
    private Drawer drawer = Drawer.INSTANCE;
    private SudokuBoard board;
    private static GameDifficulty difficulty = GameDifficulty.EASY;
    private List<SudokuBoard> backTrackList = new ArrayList<>();

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

    public static GameDifficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(GameDifficulty difficulty) {
        GameProcessor.difficulty = difficulty;
    }

    public void solveRandomSudokuElement(SudokuBoard board) {
        board.calculateBoard();
        List<SudokuElement> unsolvedSudokuElementList = board.getUnsolvedSudokuElements();
        Random random = new Random();
        SudokuElement solvedElement;
        if (unsolvedSudokuElementList.size() > 0) {
            solvedElement = unsolvedSudokuElementList
                    .get(random.nextInt(0, unsolvedSudokuElementList.size()));
        } else {
            solvedElement = null;
        }
        SudokuElement elementOneAvailableValue = unsolvedSudokuElementList.stream()
                .filter(e -> e.getAvailableFieldValues().size() == 1)
                .findFirst().orElse(null);

        if (elementOneAvailableValue != null) solvedElement = elementOneAvailableValue;
        if ((solvedElement != null) && (solvedElement.getAvailableFieldValues().size() > 0))  {
            List<Integer> availableFieldValuesList = solvedElement.getAvailableFieldValues().stream()
                    .collect(Collectors.toList());
            solvedElement.setFieldValue(availableFieldValuesList
                    .get(random.nextInt(0, availableFieldValuesList.size())));
            board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());
            addCurrentBoardToBackTrackList();
        } else {
            System.out.println("Unable to solve!");    //TODO - handle this situation
        }
    }

    public void addCurrentBoardToBackTrackList() {
        backTrackList.add(getBoard().getNumberOfCopy(), getBoard());
        try {
            setBoard(getBoard().deepCopy());
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception when trying to clone current board: " + e);
        }
    }

    public void setPreviousBoard() {
        setBoard(getBackTrackList().get(getBoard().getNumberOfCopy() - 1));
    }

    public void setNextBoard() {
        if ((getBackTrackList().contains(getBoard().getNumberOfCopy() + 1))) {
            setBoard(getBackTrackList().get(getBoard().getNumberOfCopy() + 1));
        }
    }

    public List<SudokuBoard> getBackTrackList() {
        return backTrackList;
    }

    public void setBackTrackList(List<SudokuBoard> backTrackList) {
        this.backTrackList = backTrackList;
    }

    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
        return null;
    }
}
