package com.kodilla.sudokujavafx.data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SudokuElement {
    private int fieldValue;
    private Set<Integer> availableFieldValues;
    private int rowIndex;
    private int colIndex;
    private boolean fixed;

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public SudokuElement(int fieldValue, int rowIndex, int colIndex) {
        this.fieldValue = fieldValue;
        availableFieldValues = new HashSet<>(Set.of(0,1,2,3,4,5,6,7,8,9));
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    @Override
    public String toString() {
        return Integer.toString(fieldValue);
    }

    public int getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Set<Integer> getAvailableFieldValues() {
        return availableFieldValues;
    }

    public void setAvailableFieldValues(Set<Integer> availableFieldValues) {
        this.availableFieldValues = availableFieldValues;
    }
    public void calculateAvailableFieldValues(SudokuBoard board) {
        Set<Integer> resultValues = new HashSet<>(Set.of(0,1,2,3,4,5,6,7,8,9));
        Set<Integer> actualRowSet = board.getRowValues(getRowIndex()).stream().collect(Collectors.toSet());
        resultValues.removeAll(actualRowSet);
        Set<Integer> actualColSet = board.getColValues(getColIndex()).stream().collect(Collectors.toSet());
        resultValues.removeAll(actualColSet);
        board.updateSubBoards();
        Set<Integer> actualSubBoardSet = board.getSubBoardValues(rowIndex, colIndex).stream()
                        .collect(Collectors.toSet());
        resultValues.removeAll(actualSubBoardSet);
        this.availableFieldValues = resultValues;
    }
    public Set getAvailableFieldValues(SudokuBoard board) {
        calculateAvailableFieldValues(board);
        return this.availableFieldValues;
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
}
