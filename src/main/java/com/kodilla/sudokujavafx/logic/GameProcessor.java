package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuMove;
import com.kodilla.sudokujavafx.data.SudokuRow;
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
    private Deque<SudokuBoard> backTrackOfBoards = new ArrayDeque<>();

    private GameProcessor() {
    }
    public void processGame(Stage stage, SudokuBoard board) {

        drawer.drawMainWindow(stage, board);
    }
    public void processGame(Stage stage) {
        board = new SudokuBoard();
        drawer.drawMainWindow(stage, board);
    }


    public void addMoveToBackTrack(SudokuMove move) {
        if (backTrack.add(move)) {
            System.out.println("Added board " +
                    move.getBoard().getName() + " [copy #" +
                    move.getBoard().getNumberOfCopy() +
                    "] and move on element [" + move.getRowIndex() + "|" + move.getColIndex() + "], with new value: " + move.getNewValue() + " to backTrack");
            System.out.println("There are [" + getBackTrack().size() + "] elements in backtrack");
        }
    }

    public Deque<SudokuBoard> getBackTrackOfBoards() {
        return backTrackOfBoards;
    }

    public void setBackTrackOfBoards(Deque<SudokuBoard> backTrackOfBoards) {
        this.backTrackOfBoards = backTrackOfBoards;
    }

    public void addBoardToBackTrack(SudokuBoard board) {
        if (backTrackOfBoards.add(board)) {
            System.out.println("Added board " +
                    board.getName() + " [copy #" +
                    board.getNumberOfCopy() +
                    "] and [instance #" + board.getInstance() + "] to backTrackOfBoards");
            System.out.println("There are [" + getBackTrackOfBoards().size() + "] elements in backtrack");
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
        SudokuElement elementTwoAvailableValues = unsolvedSudokuElementList.stream()
                .filter(e -> e.getAvailableFieldValues().size() == 2)
                .findFirst().orElse(null);

        if (elementOneAvailableValue != null) {
            solvedElement = elementOneAvailableValue;
        } else if (elementTwoAvailableValues != null) {
            solvedElement = elementTwoAvailableValues;
        }
        if ((solvedElement != null) && (solvedElement.getAvailableFieldValues().size() > 0))  {
            List<Integer> availableFieldValuesList = solvedElement.getAvailableFieldValues().stream()
                    .collect(Collectors.toList());
            solvedElement.setFieldValue(availableFieldValuesList
                    .get(random.nextInt(0, availableFieldValuesList.size())));
            board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());


            addMoveToBackTrack(new SudokuMove(boardCopyToBacktrack, solvedElement.getRowIndex(), solvedElement.getColIndex(), solvedElement.getFieldValue()));


            System.out.println("Element: " + solvedElement + " - available set - " + solvedElement.getAvailableFieldValues());

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

    public Deque<SudokuMove> getBackTrack() {
        return backTrack;
    }

    public void setBackTrackList(Deque<SudokuMove> backTrack) {
        this.backTrack = backTrack;
    }

//    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
//        while (!board.isBoardSolved()) {
//            solveRandomSudokuElement(board);
//            if (board.getNumberOfSolutions() == 0) {
//                boolean end = false;
//
//                while (!end) {
//                    //SudokuMove move = getBackTrack().pollFirst();
//                    SudokuMove move = getBackTrack().pollLast();
//
//                    if ((getBackTrack().size() == 0) || (move.getBoard().getNumberOfSolutions() > 1) || ((move.getBoard().getNumberOfSolutions() == 1) && move.isPicked())) {
//                        end = true;
//                        //move = getBackTrack().peekLast();
//                        board = move.getBoard();
//
//                        if ((move.getBoard().getNumberOfSolutions() == 1) && move.isPicked()) {
//                            move.setPicked(false);
//                        } else {
//                            move.setPicked(true);
//                        }
//                        System.out.println("picked board with [" + board.getNumberOfSolutions() + "] number of solutions");
//                        board.getSudokuBoardList().get(move.getRowIndex()).getSudokuElementsList().get(move.getColIndex()).addValueToFalseFieldValues(move.getNewValue());
//                    }
//                }
//
//            }
//        }
//        return board;
//    }

    public SudokuElement solveElement(SudokuElement element) {
        if (element.getAvailableFieldValues().size() > 0) {
            Random random = new Random();
            List<Integer> availableFieldValuesList = element.getAvailableFieldValues().stream()
                    .collect(Collectors.toList());
            element.setFieldValue(availableFieldValuesList
                    .get(random.nextInt(0, availableFieldValuesList.size())));
            return element;
        } else {
            element.setFieldValue(0);
            return element;
        }
    }
    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
        boolean end = false;
        //while (!end) {
            while (board.getNumberOfSolutions() == 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                board.getSudokuBoardList().stream()
                        .flatMap(l -> l.getSudokuElementsList().stream())
                        .filter(e -> e.getAvailableFieldValues().size() == 1)
                        .forEach(element -> solveElement(element));
                board.calculateBoard();
            }
            if (board.getNumberOfSolutions() > 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                int copyNumber = 0;
                for (SudokuElement element : board.getUnsolvedSudokuElements()) {
                    for (int availableValue : element.getAvailableFieldValues()) {
                        SudokuBoard copyOfBoard = getCopyOfBoard(board);
                        copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
                        copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());
                        copyNumber = copyNumber + 1;
                        copyOfBoard.setNumberOfCopy(copyNumber);
                        addBoardToBackTrack(copyOfBoard);

//                        SudokuBoard solvedSudoku = solveSudokuBoard(copyOfBoard);
//                        if (solvedSudoku.isBoardSolved()) {
//                            return solvedSudoku;
//                        }
                    }
                }
                board = getBackTrackOfBoards().pollLast();
                board = solveSudokuBoard(board);

            } else if (board.getNumberOfSolutions() == 0) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                board = getBackTrackOfBoards().pollLast();
                board = solveSudokuBoard(board);
            }
            end = board.isBoardSolved();
        //}
        return board;
    }

//    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
//        //while (!board.isBoardSolved()) {
//            solveRandomSudokuElement(board);
//            if (board.getNumberOfSolutions() == 0) {
//                boolean end = false;
//
//                while (!end) {
//
//                    SudokuMove move = getBackTrack().pollLast();
//
//                    if (getBackTrack().size() == 0) {
//                        end = true;
//                        System.out.println("Backtrack size = 0 - can't solve this board!");
//                    } else if (move.getBoard().getNumberOfSolutions() > 1) {
//                        end = true;
//                        board = move.getBoard();
//                        System.out.println("picked board with [" + board.getNumberOfSolutions() + "] number of solutions");
//                        System.out.println("Trying other solutions");
//
//                        board.getSudokuBoardList().get(move.getRowIndex()).getSudokuElementsList().get(move.getColIndex()).addValueToFalseFieldValues(move.getNewValue());
//                        solveRandomSudokuElement(board);
//                    }
//                }
//            }
//        //}
//        return board;
//    }

//    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
//        getBackTrack().clear();
//
//        while (!board.isBoardSolved()) {
//            int i = 1;
//            boolean end = false;
//            while (!end) {
//                int counter = 0;
//                for (SudokuRow row : board.getSudokuBoardList()) {
//                    for (SudokuElement element : row.getSudokuElementsList()) {
//                        if (!element.isFixed() && element.getFieldValue() == 0) {
//                            board.calculateBoard();
//
//                            if ((board.getNumberOfSolutions() == 0) && (getBackTrack().size() > 0)) {
//                                SudokuMove lastMove = getBackTrack().pollLast();
//                                SudokuBoard newBoard = lastMove.getBoard();
//                                newBoard.getElementFromBoard(lastMove.getRowIndex(), lastMove.getColIndex()).addValueToFalseFieldValues(lastMove.getNewValue());
//                                setBoard(newBoard);
//
//                            } else {
//                                if (element.getAvailableFieldValues().size() == i) {
//
//                                    board.setValueElementFromBoard(element.getAvailableFieldValues().stream().toList().get(i - 1), element.getRowIndex(), element.getColIndex());
//                                    if (i > 1) {
//                                        try {
//                                            addMoveToBackTrack(new SudokuMove(board.deepCopy(), element.getRowIndex(), element.getColIndex(), element.getFieldValue()));
//                                        } catch (CloneNotSupportedException e) {
//
//                                        }
//                                    }
//
//                                    counter += 1;
//                                }
//                                if (counter == 0) {
//                                    end = true;
//                                    i += 1;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return board;
//    }

    public void restartBoard() {
        if (getBackTrack().size() > 0) {
            setBoard(getBackTrack().peekFirst().getBoard());
            getBackTrack().clear();
        }
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
