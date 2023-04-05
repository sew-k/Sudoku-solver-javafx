package com.kodilla.sudokujavafx.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuMove move = (SudokuMove) o;
        return rowIndex == move.rowIndex && colIndex == move.colIndex && newValue == move.newValue && Objects.equals(board, move.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, rowIndex, colIndex, newValue);
    }
}
