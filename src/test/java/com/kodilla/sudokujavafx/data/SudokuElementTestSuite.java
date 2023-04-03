package com.kodilla.sudokujavafx.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class SudokuElementTestSuite {

    @Test
    void testCalculateAvailableFieldValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        } catch (IOException e) {

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
}
