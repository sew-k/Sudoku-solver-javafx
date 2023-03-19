package com.kodilla.sudokujavafx.data;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    private List<SudokuRow> sudokuBoardList;
    private List<SubBoard> subBoardList;

    public SudokuBoard() {
        sudokuBoardList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sudokuBoardList.add(new SudokuRow(i));
        }
//        SubBoard.TOP_LEFT.addElementsToSubBoardElementsList(SubBoard.TOP_LEFT, this);
//        SubBoard.TOP_CENTER.addElementsToSubBoardElementsList(SubBoard.TOP_CENTER, this);
//        SubBoard.TOP_RIGHT.addElementsToSubBoardElementsList(SubBoard.TOP_RIGHT, this);
//        SubBoard.CENTER_LEFT.addElementsToSubBoardElementsList(SubBoard.CENTER_LEFT, this);
//        SubBoard.CENTER.addElementsToSubBoardElementsList(SubBoard.CENTER, this);
//        SubBoard.CENTER_RIGHT.addElementsToSubBoardElementsList(SubBoard.CENTER_RIGHT, this);
//        SubBoard.BOTTOM_LEFT.addElementsToSubBoardElementsList(SubBoard.BOTTOM_LEFT, this);
//        SubBoard.BOTTOM_CENTER.addElementsToSubBoardElementsList(SubBoard.BOTTOM_CENTER, this);
//        SubBoard.BOTTOM_RIGHT.addElementsToSubBoardElementsList(SubBoard.BOTTOM_RIGHT, this);
//        subBoardList.add(SubBoard.TOP_LEFT);
//        subBoardList.add(SubBoard.TOP_CENTER);
//        subBoardList.add(SubBoard.TOP_RIGHT);
//        subBoardList.add(SubBoard.CENTER_LEFT);
//        subBoardList.add(SubBoard.CENTER);
//        subBoardList.add(SubBoard.CENTER_RIGHT);
//        subBoardList.add(SubBoard.BOTTOM_LEFT);
//        subBoardList.add(SubBoard.BOTTOM_CENTER);
//        subBoardList.add(SubBoard.BOTTOM_RIGHT);
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


}
