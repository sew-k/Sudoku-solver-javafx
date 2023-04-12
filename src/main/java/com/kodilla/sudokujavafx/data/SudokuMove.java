package com.kodilla.sudokujavafx.data;

import java.util.Objects;

public class SudokuMove {
    private SudokuBoard board;
    private final int rowIndex;
    private final int colIndex;
    private final int newValue;

    public SudokuMove(SudokuBoard board, final int rowIndex, final int colIndex, final int newValue) {
        this.board = board;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.newValue = newValue;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getNewValue() {
        return newValue;
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
