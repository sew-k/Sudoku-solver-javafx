package com.kodilla.sudokujavafx.data;

public class SudokuMove {
    private SudokuBoard board;
    private int rowIndex;
    private int colIndex;
    private int newValue;
    private boolean picked = false;

    public SudokuMove(SudokuBoard board, int rowIndex, int colIndex, int newValue) {
        this.board = board;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.newValue = newValue;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }
}
