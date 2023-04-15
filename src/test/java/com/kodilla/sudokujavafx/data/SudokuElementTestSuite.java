package com.kodilla.sudokujavafx.data;

import com.kodilla.sudokujavafx.logic.GameProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SudokuElementTestSuite {

    @Test
    void testCalculateAvailableFieldValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        } catch (NumberFormatException e) {

        }

        //When
        SudokuElement element = board.getElementFromBoard(0,0);
        System.out.println("Available field values for element before calculation: " + element.getAvailableFieldValues());
        element.calculateAvailableFieldValues(board);
        Set<Integer> expectedSet = Set.of(4, 6, 7);
        Set<Integer> resultSet = element.getAvailableFieldValues();
        System.out.println("Available field values for element after calculation: " + resultSet);

        //Then
        Assertions.assertEquals(expectedSet, resultSet);

    }

    @Test
    void testGetAvailableFieldValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.getElementFromBoard(0,1).setFieldValue(4);
        board.getElementFromBoard(0,2).setFieldValue(5);
        board.getElementFromBoard(0,3).setFieldValue(6);
        board.getElementFromBoard(1,0).setFieldValue(7);
        board.getElementFromBoard(2,0).setFieldValue(8);

        //When
        Set<Integer> resultSet = board.getElementFromBoard(0,0).getAvailableFieldValues(board);
        Set<Integer> expectedSet = new HashSet<>(Set.of(1,2,3,9));

        //Then
        Assertions.assertEquals(expectedSet, resultSet);
    }
    @Test
    void getFirstElementSolution() {
        //Given
        SudokuElement element = new SudokuElement(0,0,0);
        element.setAvailableFieldValues(Set.of(3, 5, 7));

        //When
        int resultValue = element.getFirstElementSolution();

        //Then
        Assertions.assertTrue(element.getAvailableFieldValues().contains(resultValue));
    }
}
