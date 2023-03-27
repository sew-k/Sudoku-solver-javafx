package com.kodilla.sudokujavafx.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SubBoard {
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT;

    private List<SudokuElement> subBoardElementsList = new ArrayList<>();
    private int subBoardRowIndex;
    private int subBoardColIndex;

    SubBoard() {

    }

    public List<SudokuElement> getSubBoardElementsList() {
        return subBoardElementsList;
    }

    public void setSubBoardElementsList(List<SudokuElement> subBoardElementsList) {
        this.subBoardElementsList = subBoardElementsList;
    }


    public void addElementsToSubBoardElementsList(SubBoard subBoard, SudokuBoard board) {
        subBoard.getSubBoardElementsList().clear();
        switch (subBoard) {
            case TOP_LEFT -> {
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(0,0));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(0,1));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(0,2));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(1,0));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(1,1));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(1,2));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(2,0));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(2,1));
                board.getSubBoardList().get(0).getSubBoardElementsList().add(board.getElementFromBoard(2,2));
                setSubBoardRowIndex(0);
                setSubBoardColIndex(0);
            }
            case TOP_CENTER -> {
                subBoardElementsList.add(board.getElementFromBoard(0,3));
                subBoardElementsList.add(board.getElementFromBoard(0,4));
                subBoardElementsList.add(board.getElementFromBoard(0,5));
                subBoardElementsList.add(board.getElementFromBoard(1,3));
                subBoardElementsList.add(board.getElementFromBoard(1,4));
                subBoardElementsList.add(board.getElementFromBoard(1,5));
                subBoardElementsList.add(board.getElementFromBoard(2,3));
                subBoardElementsList.add(board.getElementFromBoard(2,4));
                subBoardElementsList.add(board.getElementFromBoard(2,5));
                setSubBoardRowIndex(0);
                setSubBoardColIndex(1);
            }
            case TOP_RIGHT -> {
                subBoardElementsList.add(board.getElementFromBoard(0,6));
                subBoardElementsList.add(board.getElementFromBoard(0,7));
                subBoardElementsList.add(board.getElementFromBoard(0,8));
                subBoardElementsList.add(board.getElementFromBoard(1,6));
                subBoardElementsList.add(board.getElementFromBoard(1,7));
                subBoardElementsList.add(board.getElementFromBoard(1,8));
                subBoardElementsList.add(board.getElementFromBoard(2,6));
                subBoardElementsList.add(board.getElementFromBoard(2,7));
                subBoardElementsList.add(board.getElementFromBoard(2,8));
                setSubBoardRowIndex(0);
                setSubBoardColIndex(2);
            }
            case CENTER_LEFT -> {
                subBoardElementsList.add(board.getElementFromBoard(3,0));
                subBoardElementsList.add(board.getElementFromBoard(3,1));
                subBoardElementsList.add(board.getElementFromBoard(3,2));
                subBoardElementsList.add(board.getElementFromBoard(4,0));
                subBoardElementsList.add(board.getElementFromBoard(4,1));
                subBoardElementsList.add(board.getElementFromBoard(4,2));
                subBoardElementsList.add(board.getElementFromBoard(5,0));
                subBoardElementsList.add(board.getElementFromBoard(5,1));
                subBoardElementsList.add(board.getElementFromBoard(5,2));
                setSubBoardRowIndex(1);
                setSubBoardColIndex(0);
            }
            case CENTER -> {
                subBoardElementsList.add(board.getElementFromBoard(3,3));
                subBoardElementsList.add(board.getElementFromBoard(3,4));
                subBoardElementsList.add(board.getElementFromBoard(3,5));
                subBoardElementsList.add(board.getElementFromBoard(4,3));
                subBoardElementsList.add(board.getElementFromBoard(4,4));
                subBoardElementsList.add(board.getElementFromBoard(4,5));
                subBoardElementsList.add(board.getElementFromBoard(5,3));
                subBoardElementsList.add(board.getElementFromBoard(5,4));
                subBoardElementsList.add(board.getElementFromBoard(5,5));
                setSubBoardRowIndex(1);
                setSubBoardColIndex(1);
            }
            case CENTER_RIGHT -> {
                subBoardElementsList.add(board.getElementFromBoard(3,6));
                subBoardElementsList.add(board.getElementFromBoard(3,7));
                subBoardElementsList.add(board.getElementFromBoard(3,8));
                subBoardElementsList.add(board.getElementFromBoard(4,6));
                subBoardElementsList.add(board.getElementFromBoard(4,7));
                subBoardElementsList.add(board.getElementFromBoard(4,8));
                subBoardElementsList.add(board.getElementFromBoard(5,6));
                subBoardElementsList.add(board.getElementFromBoard(5,7));
                subBoardElementsList.add(board.getElementFromBoard(5,8));
                setSubBoardRowIndex(1);
                setSubBoardColIndex(2);
            }
            case BOTTOM_LEFT -> {
                subBoardElementsList.add(board.getElementFromBoard(6,0));
                subBoardElementsList.add(board.getElementFromBoard(6,1));
                subBoardElementsList.add(board.getElementFromBoard(6,2));
                subBoardElementsList.add(board.getElementFromBoard(7,0));
                subBoardElementsList.add(board.getElementFromBoard(7,1));
                subBoardElementsList.add(board.getElementFromBoard(7,2));
                subBoardElementsList.add(board.getElementFromBoard(8,0));
                subBoardElementsList.add(board.getElementFromBoard(8,1));
                subBoardElementsList.add(board.getElementFromBoard(8,2));
                setSubBoardRowIndex(2);
                setSubBoardColIndex(0);
            }
            case BOTTOM_CENTER -> {
                subBoardElementsList.add(board.getElementFromBoard(6,3));
                subBoardElementsList.add(board.getElementFromBoard(6,4));
                subBoardElementsList.add(board.getElementFromBoard(6,5));
                subBoardElementsList.add(board.getElementFromBoard(7,3));
                subBoardElementsList.add(board.getElementFromBoard(7,4));
                subBoardElementsList.add(board.getElementFromBoard(7,5));
                subBoardElementsList.add(board.getElementFromBoard(8,3));
                subBoardElementsList.add(board.getElementFromBoard(8,4));
                subBoardElementsList.add(board.getElementFromBoard(8,5));
                setSubBoardRowIndex(2);
                setSubBoardColIndex(1);
            }
            case BOTTOM_RIGHT -> {
                subBoardElementsList.add(board.getElementFromBoard(6,6));
                subBoardElementsList.add(board.getElementFromBoard(6,7));
                subBoardElementsList.add(board.getElementFromBoard(6,8));
                subBoardElementsList.add(board.getElementFromBoard(7,6));
                subBoardElementsList.add(board.getElementFromBoard(7,7));
                subBoardElementsList.add(board.getElementFromBoard(7,8));
                subBoardElementsList.add(board.getElementFromBoard(8,6));
                subBoardElementsList.add(board.getElementFromBoard(8,7));
                subBoardElementsList.add(board.getElementFromBoard(8,8));
                setSubBoardRowIndex(2);
                setSubBoardColIndex(2);
            }
        }

    }

    public int getSubBoardRowIndex() {
        return subBoardRowIndex;
    }

    public void setSubBoardRowIndex(int subBoardRowIndex) {
        this.subBoardRowIndex = subBoardRowIndex;
    }

    public int getSubBoardColIndex() {
        return subBoardColIndex;
    }

    public void setSubBoardColIndex(int subBoardColIndex) {
        this.subBoardColIndex = subBoardColIndex;
    }
}
