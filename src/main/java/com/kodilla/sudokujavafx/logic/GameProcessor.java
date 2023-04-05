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
    private SudokuBoard board = new SudokuBoard();
    private static GameDifficulty difficulty = GameDifficulty.EASY;

    private Deque<SudokuMove> backTrack = new ArrayDeque<>();
    private Set<SudokuMove> falseMovesSet = new HashSet<>();
    private Deque<SudokuBoard> backTrackOfBoards = new ArrayDeque<>();
    private Deque<String> backTrackOfBoardsStringList = new ArrayDeque<>();
    private List<SudokuBoard> solvedBoardsList = new ArrayList<>();
    private List<SudokuBoard> falseBoardsList = new ArrayList<>();
    private Set<String> falseBoardsStringSet = new HashSet<>();

    private GameProcessor() {
    }
    public void processGame(Stage stage, SudokuBoard board) {

        drawer.drawMainWindow(stage, board);
    }
    public void processGame(Stage stage) {
        board = new SudokuBoard();
        drawer.drawMainWindow(stage, board);
    }

    public Set<SudokuMove> getFalseMovesSet() {
        return falseMovesSet;
    }

    public void setFalseMovesSet(Set<SudokuMove> falseMovesSet) {
        this.falseMovesSet = falseMovesSet;
    }

    public List<SudokuBoard> getFalseBoardsList() {
        return falseBoardsList;
    }

    public void setFalseBoardsList(List<SudokuBoard> falseBoardsList) {
        this.falseBoardsList = falseBoardsList;
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

    public List<SudokuBoard> getSolvedBoardsList() {
        return solvedBoardsList;
    }

    public void setSolvedBoardsList(List<SudokuBoard> solvedBoardsList) {
        this.solvedBoardsList = solvedBoardsList;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        board.calculateBoard();
        getSolvedBoardsList().clear();
        getFalseBoardsList().clear();
        getBackTrackOfBoards().clear();
        //getBackTrack().clear();
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
        System.out.println("backtrack has " + getBackTrack().size() + " elements");
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

    public Deque<String> getBackTrackOfBoardsStringList() {
        return backTrackOfBoardsStringList;
    }

    public void setBackTrackOfBoardsStringList(Deque<String> backTrackOfBoardsStringList) {
        this.backTrackOfBoardsStringList = backTrackOfBoardsStringList;
    }

    public Set<String> getFalseBoardsStringSet() {
        return falseBoardsStringSet;
    }

    public void setFalseBoardsStringSet(Set<String> falseBoardsStringSet) {
        this.falseBoardsStringSet = falseBoardsStringSet;
    }

    public SudokuElement solveElement(SudokuElement element, int indexOfAvailableValue) {

        if (element.getAvailableFieldValues().size() > 0) {

            //Random random = new Random();
//            List<Integer> availableFieldValuesList = element.getAvailableFieldValues().stream()
//                    .collect(Collectors.toList());
//            element.setFieldValue(availableFieldValuesList
//                    .get(random.nextInt(0, availableFieldValuesList.size())));
            //System.out.println("element: " + element + " possible values: " + element.getAvailableFieldValues());
            element.setFieldValue(element.getAvailableFieldValues().stream().toList().get(indexOfAvailableValue));
            return element;
        } else {
            element.setFieldValue(0);
            return element;
        }
    }

    public SudokuBoard solveSudokuBoard3(SudokuBoard board) {
        boolean end = false;
        while (!end) {
            board.calculateBoard();

            if (board.getNumberOfSolutions() == 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());

                SudokuElement element = board.getSudokuBoardList().stream()
                        .flatMap(l -> l.getSudokuElementsList().stream())
                        .filter(e -> e.getAvailableFieldValues().size() == 1 && e.getFieldValue() == 0)
                        .findFirst().orElse(null);

                SudokuElement solvedElement = solveElement(element,0);
                board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());

                System.out.println(element);
                System.out.println(solvedElement);
                //return board;
                //board = solveSudokuBoard(board);

                if (board.isBoardSolved()) {
                    end = true;
                }

            } else if (board.getNumberOfSolutions() > 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                int copyNumber = 0;
                boolean anyAddedToBackTrack = false;

                for (SudokuElement element : board.getUnsolvedSudokuElements()) {
                    for (int availableValue : element.getAvailableFieldValues()) {
                        SudokuBoard copyOfBoard = getCopyOfBoard(board);
                        copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
                        copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());

                        if (copyOfBoard.isBoardSolved()) {
                            return copyOfBoard;
                        }

                        if (!getFalseBoardsStringSet().contains(copyOfBoard.toSimpleString())) {
                            //addBoardToBackTrack(copyOfBoard);
                            copyNumber = copyNumber + 1;
                            copyOfBoard.setNumberOfCopy(copyNumber);
                            if (getBackTrackOfBoardsStringList().offer(copyOfBoard.toSimpleString())) {
                                anyAddedToBackTrack = true;
                            }
                        } else {
                            System.out.println();
                            System.out.println("REJECTED!!!");
                            System.out.println(copyOfBoard);
                            System.out.println();
                        }
                    }
                }

                if (!anyAddedToBackTrack) {
                    getBackTrackOfBoardsStringList().pollLast();
                    try {
                        getFalseBoardsStringSet().add(board.toSimpleString());
                        System.out.println("FalseBoardsStringSet() has: " + getFalseBoardsStringSet().size() + "elements");
                        board = board.setBoardFromString(getBackTrackOfBoardsStringList().peekLast());
                        System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoardsStringList().size() + "records");
                    } catch (IOException e) {

                    }
                } else {
                    try {
                        board = board.setBoardFromString(getBackTrackOfBoardsStringList().peekLast());
                        System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoardsStringList().size() + "records");
                    } catch (IOException e) {

                    }
                }

                if (board.isBoardSolved()) {
                    end = true;
                }

            } else if (board.getNumberOfSolutions() == 0) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());

                try {
                    board = board.setBoardFromString(getBackTrackOfBoardsStringList().pollLast());
                    System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoardsStringList().size() + "records");
                } catch (IOException e) {

                }
                if (getFalseBoardsStringSet().add(board.toSimpleString())) {
                    System.out.println("board: \n" + board + "\nadded to falseList");
                    System.out.println("FalseBoardsStringSet() has: " + getFalseBoardsStringSet().size() + " elements");
                }
                try {
                    board = board.setBoardFromString(getBackTrackOfBoardsStringList().pollLast());
                    System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoardsStringList().size() + "records");
                } catch (IOException e) {

                }

                System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoardsStringList().size() + "records");

                if (board.isBoardSolved()) {
                    end = true;
                }
            }
        }
        return board;
    }

    public SudokuBoard solveSudokuBoard2(SudokuBoard board) {
        while (!board.isBoardSolved()) {

                if (board.getNumberOfSolutions() == 1) {
                    System.out.println("number of solutions : " + board.getNumberOfSolutions());

                    SudokuElement element = board.getSudokuBoardList().stream()
                            .flatMap(l -> l.getSudokuElementsList().stream())
                            .filter(e -> e.getAvailableFieldValues().size() == 1 && e.getFieldValue() == 0)
                            .findFirst().orElse(null);
                    SudokuElement solvedElement = solveElement(element, 0);
                    board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());
                    board.calculateBoard();
                    System.out.println(element);
                    System.out.println(solvedElement);

                } else if (board.getNumberOfSolutions() > 1) {
                    System.out.println("number of solutions : " + board.getNumberOfSolutions());
                    int copyNumber = 0;
                    for (SudokuElement element : board.getUnsolvedSudokuElements()) {
                        element.calculateAvailableFieldValues(board);
                        for (int availableValue : element.getAvailableFieldValues()) {
                            SudokuBoard copyOfBoard = getCopyOfBoard(board);
                            copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
                            copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());
                            SudokuMove move = new SudokuMove(copyOfBoard, element.getRowIndex(), element.getColIndex(), availableValue);
                            if (!getFalseMovesSet().contains(move)) {
                                //addBoardToBackTrack(copyOfBoard);
                                copyNumber = copyNumber + 1;
                                copyOfBoard.setNumberOfCopy(copyNumber);
                                getBackTrack().offer(move);
                            } else {
                                System.out.println();
                                System.out.println("REJECTED!!!");
                                System.out.println(copyOfBoard);
                                System.out.println();
                            }
                        }
                    }
                    if (copyNumber == 0) {
                        SudokuMove falseMove = getBackTrack().pollLast();
                        getFalseMovesSet().add(falseMove);
                        board = getBackTrack().peekLast().getBoard();
                    } else {
                        board = getBackTrack().peekLast().getBoard();
                    }

                } else if (board.getNumberOfSolutions() == 0) {
                    System.out.println("number of solutions : " + board.getNumberOfSolutions());
                    SudokuMove move;
                    do {
                        move = getBackTrack().pollLast();
                    } while (move.getBoard().getNumberOfSolutions() > 1);
                    getFalseMovesSet().add(move);
                    board = getBackTrack().pollLast().getBoard();
                }

        }
        return board;
    }

    public void restartBoard() {
        if (getBackTrack().size() > 0) {
            setBoard(getBackTrack().peekFirst().getBoard());
            getBackTrack().clear();
        }
    }
    public SudokuBoard simpleSolveSudokuBoard(SudokuBoard board) {            //works good
        while (!board.isBoardSolved()) {
            solveRandomSudokuElement(board);
            if (board.getNumberOfSolutions() == 0) {
                board = getBackTrack().pollFirst().getBoard();
                getBackTrack().clear();
            }
        }
        return board;
    }

    public List<SudokuBoard> findAllSolutions(SudokuBoard board) {

        getSolvedBoardsList().clear();
        boolean end = false;

        while (!end) {

            SudokuBoard solvedBoard = solveBoard(board);

            if (board.isBoardSolved()) {


                getSolvedBoardsList().add(board);

            } else {



            }

        }
        return null;
    }
    public SudokuBoard solveBoard(SudokuBoard board) {

        boolean end = false;

        while (!end) {
            board.calculateBoard();
            System.out.println("  &&&& " + board.getElementFromBoard(0,1) +"  &&&   " +
                    board.getElementFromBoard(0,1).getFalseFieldValues() + " / " +
                    board.getElementFromBoard(0,1).getAvailableFieldValues());
            System.out.println("  &&&& " + board.getElementFromBoard(0,3) +"  &&&   " +
                    board.getElementFromBoard(0,1).getFalseFieldValues() + " / " +
                    board.getElementFromBoard(0,1).getAvailableFieldValues());
            System.out.println(board);

            if (board.isBoardSolved()) {
                return board;
            } else {

                if (board.getNumberOfSolutions() == 1) {

                    System.out.println("number of solutions: " + board.getNumberOfSolutions());

                    SudokuElement elementToSolve = board.getElementWithOneSolution();
                    int newValue = elementToSolve.getFirstElementSolution();
                    elementToSolve.setFieldValue(newValue);
                    board.updateBoardWithElement(elementToSolve);
                    System.out.println(" **** " + elementToSolve);

                } else if ((board.getNumberOfSolutions() > 1)) {

                    System.out.println("number of solutions: " + board.getNumberOfSolutions());

                    SudokuBoard copyOfBoard = getCopyOfBoard(board);
                    SudokuElement elementToSolve = board.getElementWithMultipleSolutions();
                    System.out.println(elementToSolve);
                    int newValue = elementToSolve.getFirstElementSolution();
                    elementToSolve.setFieldValue(newValue);
                    System.out.println(elementToSolve);
                    board.updateBoardWithElement(elementToSolve);

                    if (getBackTrack().offer(new SudokuMove(copyOfBoard,
                            elementToSolve.getRowIndex(), elementToSolve.getColIndex(), newValue))) {
                        System.out.println();
                        System.out.println("copied to backtrack board witch element: ");
                        System.out.println(elementToSolve);
                        System.out.println("backtrack has: " + getBackTrack().size() + "records");
                    }

                } else if (board.getNumberOfSolutions() == 0) {

                    System.out.println("number of solutions: " + board.getNumberOfSolutions());
                    System.out.println("backtrack has: " + getBackTrack().size() + "records");

                    SudokuMove lastMove = getBackTrack().pollLast();
                    if (lastMove != null) {

                        board = lastMove.getBoard();
                        board.getElementFromBoard(lastMove.getRowIndex(), lastMove.getColIndex())
                                .getFalseFieldValues().add(lastMove.getNewValue());

                        System.out.println("added value " + lastMove.getNewValue() + " in element " +
                                board.getElementFromBoard(lastMove.getRowIndex(), lastMove.getColIndex()) +
                                board.getElementFromBoard(lastMove.getRowIndex(), lastMove.getColIndex()).getFalseFieldValues());

                        System.out.println("after pollLast - backtrack has: " + getBackTrack().size() + "records");
                    }

                }

            }

        }


        return board; //temporarily
    }

}
