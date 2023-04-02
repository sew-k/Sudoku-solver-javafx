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
    private List<SudokuBoard> solvedBoardsList = new ArrayList<>();
    private List<SudokuBoard> falseBoardsList = new ArrayList<>();

    private GameProcessor() {
    }
    public void processGame(Stage stage, SudokuBoard board) {

        drawer.drawMainWindow(stage, board);
    }
    public void processGame(Stage stage) {
        board = new SudokuBoard();
        drawer.drawMainWindow(stage, board);
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
        getSolvedBoardsList().clear();
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

    public void solveRandomSudokuElementForCPU(SudokuBoard board) {
        //board.calculateBoard();
        //SudokuBoard boardCopyToBacktrack = getCopyOfBoard(board);
        List<SudokuElement> unsolvedSudokuElementList = board.getUnsolvedSudokuElements();
        Random random = new Random();
        SudokuElement solvedElement;


        SudokuElement elementOneAvailableValue = unsolvedSudokuElementList.stream()
                .filter(e -> e.getAvailableFieldValues().size() == 1)
                .findFirst().orElse(null);

        if (elementOneAvailableValue != null) {
            solvedElement = elementOneAvailableValue;
        }

//        if ((solvedElement != null) && (solvedElement.getAvailableFieldValues().size() > 0))  {
//            List<Integer> availableFieldValuesList = solvedElement.getAvailableFieldValues().stream()
//                    .collect(Collectors.toList());
//            solvedElement.setFieldValue(availableFieldValuesList
//                    .get(random.nextInt(0, availableFieldValuesList.size())));
//            board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());

//
//
//            addMoveToBackTrack();
//
//
//            System.out.println("Element: " + solvedElement + " - available set - " + solvedElement.getAvailableFieldValues());
//
//        } else {
//            System.out.println("Unable to solve!");    //TODO - handle this situation
//        }
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
            //System.out.println("element: " + element + " possible values: " + element.getAvailableFieldValues());
            return element;
        } else {
            element.setFieldValue(0);
            return element;
        }
    }
//    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
//
//        if (board.isBoardSolved()) {
//            getSolvedBoardsList().add(board);
//            System.out.println();
//            System.out.println(" !!! board saved to solved list ");
//            System.out.println(" !!! solved boards list has: " + getSolvedBoardsList().size() + " elements");
//            System.out.println();
//            return board;
//
//        } else {
//            if (board.getNumberOfSolutions() == 1) {
//                solveRandomSudokuElement(board);
//                solveSudokuBoard(board);
//            } else if (board.getNumberOfSolutions() > 1) {
//                solveRandomSudokuElement(board);
//                solveSudokuBoard(board);
//
//            } else if (board.getNumberOfSolutions() == 0) {
//                board = getBackTrackOfBoards().pollLast();
//                while (getBackTrackOfBoards().peekLast().getNumberOfSolutions() == 1)
//                board.calculateBoard();
//                board = solveSudokuBoard(board);
//            }
//        }
//        return board;
//    }
    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
        board.calculateBoard();
        if (board.isBoardSolved()) {
            getSolvedBoardsList().add(board);
            System.out.println();
            System.out.println(" !!! board saved to solved list ");
            System.out.println(" !!! solved boards list has: " + getSolvedBoardsList().size() + " elements");
            System.out.println();
            return board;

        } else {
            if (board.getNumberOfSolutions() == 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
//                board.getSudokuBoardList().stream()
//                        .flatMap(l -> l.getSudokuElementsList().stream())
//                        .filter(e -> e.getAvailableFieldValues().size() == 1)
//                        .forEach(element -> solveElement(element));
//                List<SudokuElement> elementsWithOneSolution = board.getSudokuBoardList().stream()
//                                .flatMap(l -> l.getSudokuElementsList().stream())
//                                .filter(e -> e.getAvailableFieldValues().size() == 1)
//                                .collect(Collectors.toList());
//                for (SudokuElement element : elementsWithOneSolution) {
//                    board.calculateBoard();
//                    element = solveElement(element);
//                    board.setValueElementFromBoard(element.get);
//                }

                SudokuElement element = board.getSudokuBoardList().stream()
                        .flatMap(l -> l.getSudokuElementsList().stream())
                        .filter(e -> e.getAvailableFieldValues().size() == 1 && e.getFieldValue() == 0)
                                .findFirst().orElse(null);
                SudokuElement solvedElement = solveElement(element);
                board.setValueElementFromBoard(solvedElement.getFieldValue(), solvedElement.getRowIndex(), solvedElement.getColIndex());
                board.calculateBoard();
                System.out.println(element);
                System.out.println(solvedElement);
                //return board;
                //board = solveSudokuBoard(board);

            } else if (board.getNumberOfSolutions() > 1) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                int copyNumber = 0;
                for (SudokuElement element : board.getUnsolvedSudokuElements()) {
                    for (int availableValue : element.getAvailableFieldValues()) {
                        SudokuBoard copyOfBoard = getCopyOfBoard(board);
                        copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
                        copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());
                        copyNumber = copyNumber + 1;
                        copyOfBoard.setNumberOfCopy(copyNumber);
                        if (!getFalseBoardsList().contains(copyOfBoard)) {
                            addBoardToBackTrack(copyOfBoard);
                        } else {
                            System.out.println();
                            System.out.println("REJECTED!!!");
                            System.out.println(copyOfBoard);
                            System.out.println();
                        }
                    }
                }
                board = getBackTrackOfBoards().peekLast();
                System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoards().size() + "records");
//                board.calculateBoard();
//                board = solveSudokuBoard(board);

            } else if (board.getNumberOfSolutions() == 0) {
                System.out.println("number of solutions : " + board.getNumberOfSolutions());
                board = getBackTrackOfBoards().pollLast();
                if (getFalseBoardsList().add(board)) {
                    System.out.println("board: \n" + board + "\nadded to falseList");
                }
                board = getBackTrackOfBoards().pollLast();
                System.out.println("taken board [copy #" + board.getNumberOfCopy() + ", instance #" + board.getInstance() + "] now backtrack has " + getBackTrackOfBoards().size() + "records");
//                board.calculateBoard();
//                board = solveSudokuBoard(board);
            }
            board = solveSudokuBoard(board);
        }

        return board;
    }

    public List<SudokuBoard> getAllPossibleSolutions(SudokuBoard board) {
        List<SudokuBoard> solvedBoardsList = new ArrayList<>();
        SudokuBoard firstSolved = solveSudokuBoard(board);
        solvedBoardsList.add(firstSolved);
        for (SudokuBoard boardFromBackTrack : getBackTrackOfBoards()) {
            boardFromBackTrack = solveSudokuBoard(boardFromBackTrack);
            if (boardFromBackTrack.isBoardSolved()) {
                solvedBoardsList.add(boardFromBackTrack);
            }
        }
        return solvedBoardsList;
    }

    public void findAllSolutions(SudokuBoard board) {

            if (board.getNumberOfSolutions() == 1) {
                board.getSudokuBoardList().stream()
                        .flatMap(l -> l.getSudokuElementsList().stream())
                        .filter(e -> e.getAvailableFieldValues().size() == 1)
                        .forEach(element -> solveElement(element));
                board = solveSudokuBoard(board);
                if (board.isBoardSolved()) {
                    getSolvedBoardsList().add(board);
                    System.out.println("added in number of sol 1");
                }
            } else if (board.getNumberOfSolutions() > 1) {
                int copyNumber = 0;
                for (SudokuElement element : board.getUnsolvedSudokuElements()) {
                    for (int availableValue : element.getAvailableFieldValues()) {
                        SudokuBoard copyOfBoard = getCopyOfBoard(board);
                        copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
                        copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());
                        copyNumber = copyNumber + 1;
                        copyOfBoard.setNumberOfCopy(copyNumber);
                        addBoardToBackTrack(copyOfBoard);
                    }
                }
                board = getBackTrackOfBoards().pollLast();
                board = solveSudokuBoard(board);
                if (board.isBoardSolved()) {
                    getSolvedBoardsList().add(board);
                    System.out.println("added in number of sol > 1");
                }

            } else if (board.getNumberOfSolutions() == 0) {
                board = getBackTrackOfBoards().pollLast();
                board = solveSudokuBoard(board);
                if (board.isBoardSolved()) {
                    getSolvedBoardsList().add(board);
                    System.out.println("added in number of sol 0");
                }
            }
            if (getBackTrackOfBoards().size() > 0) {
                board = getBackTrackOfBoards().pollLast();
                board = solveSudokuBoard(board);
                if (board.isBoardSolved()) {
                    getSolvedBoardsList().add(board);
                    System.out.println("added in number of sol backtrack > 0");
                }
            }
    }

//    public SudokuBoard solveSudokuBoard(SudokuBoard board) {
//        boolean end = false;
//        //while (!end) {
//        while (board.getNumberOfSolutions() == 1) {
//            System.out.println("number of solutions : " + board.getNumberOfSolutions());
//            board.getSudokuBoardList().stream()
//                    .flatMap(l -> l.getSudokuElementsList().stream())
//                    .filter(e -> e.getAvailableFieldValues().size() == 1)
//                    .forEach(element -> solveElement(element));
//            board.calculateBoard();
//        }
//        if (board.getNumberOfSolutions() > 1) {
//            System.out.println("number of solutions : " + board.getNumberOfSolutions());
//            int copyNumber = 0;
//            for (SudokuElement element : board.getUnsolvedSudokuElements()) {
//                for (int availableValue : element.getAvailableFieldValues()) {
//                    SudokuBoard copyOfBoard = getCopyOfBoard(board);
//                    copyOfBoard.setInstance(copyOfBoard.getUnsolvedSudokuElements().size());
//                    copyOfBoard.setValueElementFromBoard(availableValue, element.getRowIndex(), element.getColIndex());
//                    copyNumber = copyNumber + 1;
//                    copyOfBoard.setNumberOfCopy(copyNumber);
//                    addBoardToBackTrack(copyOfBoard);
//
////                        SudokuBoard solvedSudoku = solveSudokuBoard(copyOfBoard);
////                        if (solvedSudoku.isBoardSolved()) {
////                            return solvedSudoku;
////                        }
//                }
//            }
//            board = getBackTrackOfBoards().pollLast();
//            board = solveSudokuBoard(board);
//
//        } else if (board.getNumberOfSolutions() == 0) {
//            System.out.println("number of solutions : " + board.getNumberOfSolutions());
//            board = getBackTrackOfBoards().pollLast();
//            board = solveSudokuBoard(board);
//        }
//        end = board.isBoardSolved();
//        //}
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
