package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuMove;
import com.kodilla.sudokujavafx.data.SudokuRow;
import com.kodilla.sudokujavafx.presentation.Drawer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public enum GameProcessor {
    INSTANCE;
    private Drawer drawer = Drawer.INSTANCE;
    private SudokuBoard board;
    private SudokuBoard originalBoard;
    private static GameDifficulty difficulty = GameDifficulty.EASY;  //default

    private Deque<SudokuMove> backTrack = new ArrayDeque<>();

    private List<SudokuBoard> solvedBoardsList = new ArrayList<>();

    private GameProcessor() {
    }

    public void processGame(Stage stage) {
        board = new SudokuBoard();
        drawer.drawMainWindow(stage, board);
    }

    public SudokuBoard getOriginalBoard() {
        System.out.println("original board: " + originalBoard);
        return originalBoard;
    }

    public void setOriginalBoard(SudokuBoard originalBoard) {

        this.originalBoard = getCopyOfBoard(originalBoard);
    }

    public void addMoveToBackTrack(SudokuMove move) {
        if (getBackTrack().add(move)) {
            System.out.println("Added board " +
                    move.getBoard().getName() +
                    " and move on element [" + move.getRowIndex() + "|" + move.getColIndex() + "], with new value: " + move.getNewValue() + " to backTrack");
            System.out.println("There are [" + getBackTrack().size() + "] elements in backtrack");
        }
        //getBackTrack().add(move);
    }

    public void exitGame(Stage stage) {
        stage.close();
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public List<SudokuBoard> getSolvedBoardsList() {
        return solvedBoardsList;
    }

    public void setSolvedBoardsList(List<SudokuBoard> solvedBoardsList) {
        this.solvedBoardsList = solvedBoardsList;
    }

    public void setBoard(SudokuBoard board) {
        this.board = new SudokuBoard();
        this.board = getCopyOfBoard(board);
        board.calculateBoard();
        getSolvedBoardsList().clear();
    }

    public static GameDifficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(GameDifficulty difficulty) {
        GameProcessor.difficulty = difficulty;
    }

    public void solveRandomSudokuElement(SudokuBoard board) {
        board.calculateBoard();
        SudokuBoard boardCopyToBacktrack = getCopyOfBoard(board);
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

        if (elementOneAvailableValue != null) {
            solvedElement = elementOneAvailableValue;
        }
        if ((solvedElement != null) && (solvedElement.getAvailableFieldValues().size() > 0))  {
            List<Integer> availableFieldValuesList = solvedElement.getAvailableFieldValues().stream()
                    .collect(Collectors.toList());
            solvedElement.setFieldValue(availableFieldValuesList
                    .get(random.nextInt(0, availableFieldValuesList.size())));

            board.updateBoardWithElement(solvedElement);
            addMoveToBackTrack(new SudokuMove(boardCopyToBacktrack, solvedElement.getRowIndex(), solvedElement.getColIndex(), solvedElement.getFieldValue()));

        } else {
            System.out.println("Unable to solve!");    //TODO - handle this situation
        }
    }

    public SudokuBoard getCopyOfBoard(SudokuBoard board) {
        try {
            SudokuBoard copyOfBoard = board.deepCopy();
            return copyOfBoard;
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception when trying to clone current board: " + e);
            return null;
        }
    }

    public void setPreviousBoard() {
        System.out.println("backtrack has " + getBackTrack().size() + " elements");
        if (getBackTrack().size() > 0) {
            SudokuBoard previousBoard = getBackTrack().pollLast().getBoard();
            setBoard(previousBoard);
        }
    }

    public Deque<SudokuMove> getBackTrack() {
        return backTrack;
    }

    public void restartBoard() {
        if (getOriginalBoard() != null) {
            setBoard(getOriginalBoard());
            getBackTrack().clear();
        }
    }
    public SudokuBoard simpleSolveSudokuBoard(SudokuBoard board) {            //works good on easy and medium boards
        while (!board.isBoardSolved()) {
            solveRandomSudokuElement(board);
            if (board.getNumberOfSolutions() == 0) {
                board = getBackTrack().pollFirst().getBoard();
                getBackTrack().clear();
            }
        }
        return board;
    }
//    public List<SudokuBoard> findAllSolutions(SudokuBoard board) {           TODO: finding all solutions and save them to list
//
//        getSolvedBoardsList().clear();
//        boolean end = false;
//
//        while (!end) {
//
//            SudokuBoard solvedBoard = solveBoard(board);
//
//            if (board.isBoardSolved()) {
//
//                getSolvedBoardsList().add(board);
//
//            } else {
//
//            }
//
//        }
//        return null;
//    }
    public SudokuBoard solveBoard(SudokuBoard board) {

        boolean end = false;

        while (!end) {
            board.calculateBoard();
            if (board.isBoardSolved()) {
                return board;
            } else {

                if (board.getNumberOfSolutions() == 1) {
                    SudokuElement elementToSolve = board.getElementWithOneSolution();
                    elementToSolve.setFieldValue(elementToSolve.getFirstElementSolution());

                } else if ((board.getNumberOfSolutions() > 1)) {

                    SudokuBoard copyOfBoard = getCopyOfBoard(board);
                    SudokuElement elementToSolve = board.getElementWithMultipleSolutions();
                    int newValue = elementToSolve.getFirstElementSolution();
                    elementToSolve.setFieldValue(newValue);
                    board.updateBoardWithElement(elementToSolve);

                    if (getBackTrack().offer(new SudokuMove(copyOfBoard,
                            elementToSolve.getRowIndex(), elementToSolve.getColIndex(), newValue))) {
                    }

                } else if (board.getNumberOfSolutions() == 0) {

                    SudokuMove lastMove = getBackTrack().pollLast();
                    if (lastMove != null) {

                        board = lastMove.getBoard();
                        board.getElementFromBoard(lastMove.getRowIndex(), lastMove.getColIndex())
                                .getFalseFieldValues().add(lastMove.getNewValue());
                    } else {
                        end = true;
                        return null;
                    }
                }
            }
        }
        return board;
    }
}
