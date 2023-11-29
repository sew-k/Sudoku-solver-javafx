package com.kodilla.sudokujavafx.data;

import com.kodilla.sudokujavafx.logic.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuBoard implements Cloneable {
    private String name;
    private List<SudokuRow> sudokuBoardList;

    public SudokuBoard() {
        sudokuBoardList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sudokuBoardList.add(new SudokuRow(i));
        }
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard copyOfBoard = (SudokuBoard) this.clone();
        copyOfBoard.setName(this.getName());
        copyOfBoard.sudokuBoardList = new ArrayList<>();
        for (SudokuRow row : getSudokuBoardList()) {
            SudokuRow copyOfRow = new SudokuRow(row.getRowNumber());
            for (SudokuElement element : row.getSudokuElementsList()) {
                SudokuElement copyOfElement = new SudokuElement(element.getFieldValue(),
                        element.getRowIndex(), element.getColIndex(),
                        element.getAvailableFieldValues(), element.isFixed());
                copyOfElement.getFalseFieldValues().addAll(element.getFalseFieldValues());
                copyOfRow.getSudokuElementsList().set(element.getColIndex(), copyOfElement);
            }
            copyOfBoard.getSudokuBoardList().add(copyOfRow);
        }
        return copyOfBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SudokuElement getElementFromBoard(int row, int col) {
        return getSudokuBoardList().get(row).getSudokuElementsList().get(col);
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

    public Set<Integer> getSubBoard(int indexOfFirstElement) {
        String boardString = toSimpleString();
        String resultString =  Stream.of(boardString)
                .map(s ->   (s.substring(indexOfFirstElement, indexOfFirstElement + 3) +
                            s.substring(indexOfFirstElement + 9, indexOfFirstElement + 12) +
                            s.substring(indexOfFirstElement + 18, indexOfFirstElement + 21)))
                .collect(Collectors.joining());
        Set<Integer> resultSet = new HashSet<>();
        for (int i = 0; i < resultString.length(); i++) {
            resultSet.add(Integer.parseInt(resultString.substring(i, i +1)));
        }
        resultSet.remove(0);
        return resultSet;
    }

    public Set<Integer> getSubBoardValues(int rowIndex, int colIndex) {
        if (rowIndex < 3 && colIndex < 3) {
            return getSubBoard(0);
        } else if (rowIndex < 3 && colIndex >= 3 && colIndex < 6) {
            return getSubBoard(3);
        } else if (rowIndex < 3 && colIndex >= 6) {
            return getSubBoard(6);
        } else if (rowIndex >= 3 && rowIndex < 6 && colIndex < 3) {
            return getSubBoard(27);
        } else if (rowIndex >= 3 && rowIndex < 6 && colIndex >= 3 && colIndex < 6) {
            return getSubBoard(30);
        } else if (rowIndex >= 3 && rowIndex < 6 && colIndex >= 6) {
            return getSubBoard(33);
        } else if (rowIndex >= 6 && colIndex < 3) {
            return getSubBoard(54);
        } else if (rowIndex >= 6 && colIndex >= 3 && colIndex < 6) {
            return getSubBoard(57);
        } else if (rowIndex >= 6 && colIndex >= 6) {
            return getSubBoard(60);
        } else {
            return null;
        }
    }

    public void calculateBoard() {
        getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .filter(v -> v.getFieldValue() == 0)
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

    public String toSimpleString() {
        return getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .map(e -> e.getFieldValue())
                .map(i -> Integer.toString(i))
                .collect(Collectors.joining());
    }

    public List<SudokuRow> getSudokuBoardList() {
        return sudokuBoardList;
    }

    public void setBoardManually() {
        setAllElementsFixed();
    }

    public SudokuBoard setBoardFromString(String text) throws NumberFormatException {
        SudokuBoard newBoard = new SudokuBoard();
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (Validator.INSTANCE.checkFieldTextToInt(Character.toString(text.charAt(i)))) {
                    int elementValue = Integer.parseInt(Character.toString(text.charAt(i)));
                    boolean fixed = true;
                    if (elementValue == 0) fixed = false;
                    newBoard.getElementFromBoard(row,col).setFieldValue(elementValue);
                    newBoard.getElementFromBoard(row,col).setFixed(fixed);
                    i++;
                } else {
                    throw new NumberFormatException("*** invalid element value at [" + i + "] ***");
                }
            }
        }
        return newBoard;
    }

    public SudokuBoard loadBoard(File file) throws IOException {
        String textFromFile = Files.readString(file.toPath());
        String newString = "";
        for (char c : textFromFile.toCharArray()) {
            if (Validator.INSTANCE.getAvailableCharacterForSudoku().contains(c)) {
                newString = newString + c;
            }
        }
        SudokuBoard newBoard = setBoardFromString(newString);
        newBoard.setName(file.getName());
        return newBoard;
    }

    public void saveBoard(File file, SudokuBoard board) throws IOException {
        byte[] bytes = board.toString().getBytes();
        Files.write(file.toPath(), bytes);
    }

    public SudokuBoard clearBoard() {
        return new SudokuBoard();
    }

    public List<SudokuElement> getUnsolvedSudokuElements() {
        return getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .filter(e -> e.getFieldValue() == 0)
                .collect(Collectors.toList());
    }

    public int getNumberOfSolutions() {
        return getSudokuBoardList().stream()
                .flatMap(b -> b.getSudokuElementsList().stream())
                .filter(x -> !x.isFixed())
                .filter(y ->  y.getFieldValue() == 0)
                .map(e -> e.getAvailableFieldValues().size())
                .min(Integer::compareTo).orElse(-1);
    }

    public boolean isBoardCorrect() {
        return getSudokuBoardList().stream()
                .flatMap(r -> r.getSudokuElementsList().stream())
                .filter(v -> v.getFieldValue() != 0)
                .map(SudokuElement::isElementValueCorrect)
                .filter(c -> !c)
                .findAny().orElse(true);
    }

    public boolean isBoardSolved() {
        List<SudokuElement> unsolvedElements = getUnsolvedSudokuElements();
        if ((unsolvedElements.size() == 0) && (isBoardCorrect())) {
            return true;
        } else {
            return false;
        }
    }

    public void setAllElementsFixed() {
        getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .filter(e -> e.getFieldValue() != 0)
                .forEach(sudokuElement -> sudokuElement.setFixed(true));
    }

    public SudokuElement getElementWithOneSolution() {
        calculateBoard();
        return getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .filter(e -> e.getFieldValue() == 0)
                .filter(o -> o.getAvailableFieldValues().size() == 1)
                .findFirst().orElse(null);
    }

    public SudokuElement getElementWithMultipleSolutions() {
        calculateBoard();
        return getSudokuBoardList().stream()
                .flatMap(l -> l.getSudokuElementsList().stream())
                .filter(e -> e.getFieldValue() == 0)
                .filter(o -> o.getAvailableFieldValues().size() > 1)
                .findFirst().orElse(null);
    }

    public void updateBoardWithElement(SudokuElement element) {
        getElementFromBoard(element.getRowIndex(), element.getColIndex()).setFieldValue(element.getFieldValue());
    }
}
