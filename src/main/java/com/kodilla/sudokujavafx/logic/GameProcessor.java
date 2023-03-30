package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.presentation.Drawer;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public enum GameProcessor {
    INSTANCE;
    private Drawer drawer = Drawer.INSTANCE;
    private SudokuBoard board = new SudokuBoard();
    private static GameDifficulty difficulty = GameDifficulty.EASY;
    //private List<SudokuBoard> backTrackList = new ArrayList<>();
    private Deque<SudokuBoard> backTrack = new ArrayDeque<>();

    private GameProcessor() {
    }
    public void processGame(Stage stage, SudokuBoard board) {

        drawer.drawMainWindow(stage, board);
    }
    public void processGame(Stage stage) {
        board = new SudokuBoard();
        //addBoardToBackTrack(board);
        drawer.drawMainWindow(stage, board);
    }

    public void addBoardToBackTrack(SudokuBoard board) {

        if (backTrack.add(board)) {
            System.out.println("Added board " +
                    getBoard().getName() + " [copy #" +
                    getBoard().getNumberOfCopy() +
                    "] to backTrack");
            System.out.println("There are [" + getBackTrack().size() + "] elements in backtrack");
        }
    }

    public void exitGame(Stage stage) {
        stage.close();
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        System.out.println("Board '" + getBoard().getName() + " /#" + getBoard().getNumberOfCopy() + "' set as current");
    }

    public static GameDifficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(GameDifficulty difficulty) {
        GameProcessor.difficulty = difficulty;
    }

    public void solveRandomSudokuElement(SudokuBoard board) {
        board.calculateBoard();
        addBoardToBackTrack(getCopyOfBoard(board));
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

            //setBoard(getCopyOfBoard(board));

        } else {
            System.out.println("Unable to solve!");    //TODO - handle this situation
        }
    }

//    public void addCurrentBoardToBackTrackList() {
//            backTrackList.add(getBoard());
//            System.out.println("Added board " + getBoard().getName() + " [copy #" + getBoard().getNumberOfCopy() + "] to backTrackList");
//    }
    public void setNextCopyOfBoardAsCurrent() {
        try {
            SudokuBoard copyOfBoard = getBoard().deepCopy();
            //backTrackList.add(copyOfBoard.getNumberOfCopy(), copyOfBoard);
            //
            // backTrackList.add(copyOfBoard);
            setBoard(copyOfBoard);
            System.out.println("Current board is: " + copyOfBoard.getName() + " [copy #" + copyOfBoard.getNumberOfCopy() + "]");
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception when trying to clone current board: " + e);
        }
    }
    public SudokuBoard getCopyOfBoard(SudokuBoard board) {
        try {
            SudokuBoard copyOfBoard = board.deepCopy();
            System.out.println("Board: " + board.getName() + " [copy #" + board.getNumberOfCopy() + "] copied:");
            System.out.println(" - " + copyOfBoard.getName() + " [copy #" + copyOfBoard.getNumberOfCopy() + "]");
            return copyOfBoard;
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception when trying to clone current board: " + e);
            return null;
        }
    }

    public void setPreviousBoard() {
//        try {
//            SudokuBoard previousBoard = getBackTrack().pollLast();
//            setBoard(previousBoard);
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("There is no board in backtrack! (" + e + ")");
//        }
            SudokuBoard previousBoard = getBackTrack().pollLast();
            if (previousBoard != null) {
                setBoard(previousBoard);
            }
    }

    public void setNextBoard() {

    }

    public Deque<SudokuBoard> getBackTrack() {
        return backTrack;
    }

    public void setBackTrackList(Deque<SudokuBoard> backTrackList) {
        this.backTrack = backTrackList;
    }

    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
        return null;
    }
}
