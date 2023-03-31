package com.kodilla.sudokujavafx.data;

public class SudokuMove {
    private SudokuBoard board;
    private SudokuElement element;
    private int newValue;

    public SudokuMove(SudokuBoard board, SudokuElement element, int newValue) {
        this.board = board;
        this.element = element;
        this.newValue = newValue;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }

    public SudokuElement getElement() {
        return element;
    }

    public void setElement(SudokuElement element) {
        this.element = element;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }
}
