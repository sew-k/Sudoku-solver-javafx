package com.kodilla.sudokujavafx.data;

import com.kodilla.sudokujavafx.logic.GameProcessor;
import com.kodilla.sudokujavafx.logic.Validator;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuBoard {
    private List<SudokuRow> sudokuBoardList;
    private List<SubBoard> subBoardList = new ArrayList<>(Arrays.asList(
            SubBoard.TOP_LEFT, SubBoard.TOP_CENTER, SubBoard.TOP_RIGHT,
            SubBoard.CENTER_LEFT, SubBoard.CENTER, SubBoard.CENTER_RIGHT,
            SubBoard.BOTTOM_LEFT, SubBoard.BOTTOM_CENTER, SubBoard.BOTTOM_RIGHT));
//    static {
//        subBoardList.add(SubBoard.TOP_LEFT);
//        subBoardList.add(SubBoard.TOP_CENTER);
//        subBoardList.add(SubBoard.TOP_RIGHT);
//        subBoardList.add(SubBoard.CENTER_LEFT);
//        subBoardList.add(SubBoard.CENTER);
//        subBoardList.add(SubBoard.CENTER_RIGHT);
//        subBoardList.add(SubBoard.BOTTOM_LEFT);
//        subBoardList.add(SubBoard.BOTTOM_CENTER);
//        subBoardList.add(SubBoard.BOTTOM_RIGHT);
//    }

    public SudokuBoard() {
        sudokuBoardList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sudokuBoardList.add(new SudokuRow(i));
        }
    }

    public void updateSubBoards() {
        SubBoard.TOP_LEFT.addElementsToSubBoardElementsList(SubBoard.TOP_LEFT, this);
        SubBoard.TOP_CENTER.addElementsToSubBoardElementsList(SubBoard.TOP_CENTER, this);
        SubBoard.TOP_RIGHT.addElementsToSubBoardElementsList(SubBoard.TOP_RIGHT, this);
        SubBoard.CENTER_LEFT.addElementsToSubBoardElementsList(SubBoard.CENTER_LEFT, this);
        SubBoard.CENTER.addElementsToSubBoardElementsList(SubBoard.CENTER, this);
        SubBoard.CENTER_RIGHT.addElementsToSubBoardElementsList(SubBoard.CENTER_RIGHT, this);
        SubBoard.BOTTOM_LEFT.addElementsToSubBoardElementsList(SubBoard.BOTTOM_LEFT, this);
        SubBoard.BOTTOM_CENTER.addElementsToSubBoardElementsList(SubBoard.BOTTOM_CENTER, this);
        SubBoard.BOTTOM_RIGHT.addElementsToSubBoardElementsList(SubBoard.BOTTOM_RIGHT, this);
    }

    public SudokuElement getElementFromBoard(int row, int col) {
        return sudokuBoardList.stream()
                .flatMap(r -> r.getSudokuElementsList().stream())
                .filter((e) -> e.getRowIndex() == row && e.getColIndex() == col)
                .findAny().get();
    }

    public void setValueElementFromBoard(int val, int row, int col) {
        getElementFromBoard(row, col).setFieldValue(val);
    }
    public void setValueElementFromBoard(int val, int row, int col, boolean fixed) {
        getElementFromBoard(row, col).setFieldValue(val);
        getElementFromBoard(row, col).setFixed(fixed);
    }

    public List<Integer> getRowValues(int rowIndex) {
        return getSudokuBoardList().get(rowIndex)
                .getSudokuElementsList().stream()
                .map(e -> e.getFieldValue())
                .collect(Collectors.toList());
    }
    public List<Integer> getColValues(int colIndex) {
        return getSudokuBoardList().stream()
                .flatMap(r -> r.getSudokuElementsList().stream())
                .filter(e -> e.getColIndex() == colIndex)
                .map(v -> v.getFieldValue())
                .collect(Collectors.toList());
    }
    public List<Integer> getSubBoardValues(int rowIndex, int colIndex) {
        updateSubBoards();
        if (rowIndex < 3 && colIndex < 3) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 0)
                    .filter(sr -> sr.getSubBoardRowIndex() == 0)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex < 3 && colIndex >= 3 && colIndex < 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 1)
                    .filter(sr -> sr.getSubBoardRowIndex() == 0)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex < 3 && colIndex >= 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 2)
                    .filter(sr -> sr.getSubBoardRowIndex() == 0)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 3 && rowIndex < 6 && colIndex < 3) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 0)
                    .filter(sr -> sr.getSubBoardRowIndex() == 1)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 3 && rowIndex < 6 && colIndex >= 3 && colIndex < 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 1)
                    .filter(sr -> sr.getSubBoardRowIndex() == 1)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 3 && rowIndex < 6 && colIndex >= 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 2)
                    .filter(sr -> sr.getSubBoardRowIndex() == 1)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 6 && colIndex < 3) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 0)
                    .filter(sr -> sr.getSubBoardRowIndex() == 2)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 6 && colIndex >= 3 && colIndex < 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 1)
                    .filter(sr -> sr.getSubBoardRowIndex() == 2)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else if (rowIndex >= 6 && colIndex >= 6) {
            return getSubBoardList().stream()
                    .filter(sc -> sc.getSubBoardColIndex() == 2)
                    .filter(sr -> sr.getSubBoardRowIndex() == 2)
                    .flatMap(s -> s.getSubBoardElementsList().stream())
                    .map(e -> e.getFieldValue())
                    .collect(Collectors.toList());
        }
        else {
            return null;
        }
    }
    public void calculateBoard() {
        getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .forEach(e -> e.calculateAvailableFieldValues(this));
    }

    @Override
    public String toString() {
        String boardString = "";
        for (SudokuRow row : sudokuBoardList) {
            boardString = boardString + row.toString() + "\n";
        }
        return boardString;
    }

    public List<SudokuRow> getSudokuBoardList() {
        return sudokuBoardList;
    }

    public void setSudokuBoardList(List<SudokuRow> sudokuBoardList) {
        this.sudokuBoardList = sudokuBoardList;
    }

    public void newRandomBoard() {

    }

    public void setBoardManually() {

    }
    public SudokuBoard setBoardFromString(String text) throws IOException {
        SudokuBoard newBoard = new SudokuBoard();
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (Validator.INSTANCE.checkFieldTextToInt(Character.toString(text.charAt(i)))) {
                    int elementValue = Integer.parseInt(Character.toString(text.charAt(i)));
                    boolean fixed = true;
                    if (elementValue == 0) fixed = false;
                    newBoard.setValueElementFromBoard(elementValue, row, col, fixed);
                    System.out.println("elementValue: " + elementValue);
                    System.out.println("i: " + i);
                    i++;
                } else {
                    throw new IOException("*** invalid element value at [" + i + "] ***");
                }
            }
        }
        return newBoard;
    }

    public void loadBoard(File file) {
        System.out.println(file);
        try {
            System.out.println("Loading the board");
            String textFromFile = Files.readString(file.toPath());
            String newString = "";
            for (char c : textFromFile.toCharArray()) {
                if (Validator.INSTANCE.getAvailableCharacterForSudoku().contains(c)) {
                    newString = newString + c;
                }
            }
            System.out.println(newString);
            SudokuBoard newBoard = setBoardFromString(newString);
            System.out.println("board");
            System.out.println(newBoard);
            GameProcessor.INSTANCE.setBoard(newBoard);
            System.out.println("Successful loaded board: \n" + GameProcessor.INSTANCE.getBoard());

        } catch (IOException e) {
            System.out.println("Exception:" + e);
        }
    }
    public void saveBoard(File file, SudokuBoard board) throws IOException {
        byte[] bytes = board.toString().getBytes();
        Files.write(file.toPath(), bytes);
    }
    public SudokuBoard clearBoard() {
        SudokuBoard board = new SudokuBoard();
        System.out.println(board);
        return board;
    }

    public List<SubBoard> getSubBoardList() {
        return subBoardList;
    }
}
