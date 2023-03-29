package com.kodilla.sudokujavafx.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuRow {
    private List<SudokuElement> sudokuElementsList;
    private int rowNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuRow sudokuRow = (SudokuRow) o;

        if (rowNumber != sudokuRow.rowNumber) return false;
        return sudokuElementsList.equals(sudokuRow.sudokuElementsList);
    }

    @Override
    public int hashCode() {
        int result = sudokuElementsList.hashCode();
        result = 31 * result + rowNumber;
        return result;
    }

    public SudokuRow(int rowNumber) {
        sudokuElementsList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {

//            //TEMPORARILY                   //TODO
//            Random random = new Random();
//            int fieldValue = random.nextInt(0,10);

            sudokuElementsList.add(new SudokuElement(0, rowNumber, i));
        }
    }

    @Override
    public String toString() {
        String rowString = "| ";
        for (SudokuElement element : sudokuElementsList) {
            rowString = rowString + element.getFieldValue() + "| ";
        }
        return rowString;
    }

    public List<SudokuElement> getSudokuElementsList() {
        return sudokuElementsList;
    }

    public void setSudokuElementsList(List<SudokuElement> sudokuElementsList) {
        this.sudokuElementsList = sudokuElementsList;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
