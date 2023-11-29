package com.kodilla.sudokujavafx.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SudokuElement {
    private int fieldValue;
    private Set<Integer> availableFieldValues;
    private Set<Integer> falseFieldValues = new HashSet<>();

    private final int rowIndex;
    private final int colIndex;
    private boolean fixed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuElement element = (SudokuElement) o;
        return fieldValue == element.fieldValue
                && rowIndex == element.rowIndex
                && colIndex == element.colIndex
                && fixed == element.fixed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldValue, rowIndex, colIndex, fixed);
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
        if (fixed) {
            getAvailableFieldValues().clear();
        }
    }

    public SudokuElement(int fieldValue, final int rowIndex, final int colIndex) {
        this.fieldValue = fieldValue;
        availableFieldValues = new HashSet<>(Set.of(0,1,2,3,4,5,6,7,8,9));
        falseFieldValues = new HashSet<>();
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public SudokuElement(int fieldValue, final int rowIndex, final int colIndex, Set<Integer> availableFieldValues, boolean fixed) {
        this.fieldValue = fieldValue;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.availableFieldValues = availableFieldValues;
        this.fixed = fixed;
    }

    public Set<Integer> getFalseFieldValues() {
        return falseFieldValues;
    }

    @Override
    public String toString() {
        return "[" + getRowIndex() + ";" + getColIndex() + "]:" + fieldValue;
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
        Set<Integer> actualSubBoardSet = board.getSubBoardValues(getRowIndex(), getColIndex());
        resultValues.removeAll(actualSubBoardSet);
        resultValues.removeAll(getFalseFieldValues());
        setAvailableFieldValues(resultValues);
    }

    public Set getAvailableFieldValues(SudokuBoard board) {
        calculateAvailableFieldValues(board);
        return this.availableFieldValues;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean isElementValueCorrect() {
        if (!isFixed()) {
            if (getAvailableFieldValues().contains(getFieldValue())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public int getFirstElementSolution() {
        return getAvailableFieldValues().stream()
                .findFirst().orElse(0);
    }
}
