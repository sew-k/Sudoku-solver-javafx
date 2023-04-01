package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuMove;
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
    private Deque<SudokuMove> backTrack = new ArrayDeque<>();

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

    public void addMoveToBackTrack(SudokuBoard board, SudokuElement element, int newValue) {
        SudokuMove move = new SudokuMove(board, element, newValue);
        if (backTrack.add(move)) {
            System.out.println("Added board " +
                    getBoard().getName() + " [copy #" +
                    getBoard().getNumberOfCopy() +
                    "] and move on element " + element + ", with new value: " + newValue + " to backTrack");
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
        //board.calculateBoard();
        SudokuBoard boardCopyToBacktrack = getCopyOfBoard(board);
        //addMoveToBackTrack(getCopyOfBoard(board), );
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

            addMoveToBackTrack(boardCopyToBacktrack, solvedElement, solvedElement.getFieldValue());
            //setBoard(getCopyOfBoard(board));

        } else {
            System.out.println("Unable to solve!");    //TODO - handle this situation
        }
    }


    public void setNextCopyOfBoardAsCurrent() {
        try {
            SudokuBoard copyOfBoard = getBoard().deepCopy();

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

            if (getBackTrack().size() > 0) {
                SudokuBoard previousBoard = getBackTrack().pollLast().getBoard();
                setBoard(previousBoard);
            }

    }

    public void setNextBoard() {

    }

    public Deque<SudokuMove> getBackTrack() {
        return backTrack;
    }

    public void setBackTrackList(Deque<SudokuMove> backTrack) {
        this.backTrack = backTrack;
    }

    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
        while (!board.isBoardSolved()) {
            solveRandomSudokuElement(board);
            if (board.getNumberOfSolutions() == 0) {
                boolean end = false;

                while (!end) {
                    //SudokuMove move = getBackTrack().pollFirst();
                    SudokuMove move = getBackTrack().pollLast();

                    if ((getBackTrack().size() == 0) || (move.getBoard().getNumberOfSolutions() > 1)) {
                        end = true;
                        board = move.getBoard();
                        System.out.println("picked board with [" + board.getNumberOfSolutions() + "] number of solutions");
                        int row = move.getElement().getRowIndex();
                        int col = move.getElement().getColIndex();
                        int val = move.getNewValue();
                        board.getSudokuBoardList().get(row).getSudokuElementsList().get(col).addValueToFalseFieldValues(val);

                        //TODO - how to save that value to sudoku Element ?
                    }
                }

            }
        }
        return board;
    }
    public SudokuBoard simpleSolveSudokuBoard(SudokuBoard board) {
        while (!board.isBoardSolved()) {
            solveRandomSudokuElement(board);
            if (board.getNumberOfSolutions() == 0) {
                board = getBackTrack().pollFirst().getBoard();
                getBackTrack().clear();
            }
        }
        return board;
    }
}
