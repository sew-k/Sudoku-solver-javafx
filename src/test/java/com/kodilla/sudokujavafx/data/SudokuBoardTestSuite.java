package com.kodilla.sudokujavafx.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class SudokuBoardTestSuite {
    @Test
    void testGetSubBoard() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        } catch (IOException e) {

        }

        //When
//        Set<Integer> expectedSet = Set.of(2, 8, 3);
//        Set<Integer> resultSet = board.getSubBoard(0);

        Set<Integer> expectedSet = Set.of(7, 8, 6);
        Set<Integer> resultSet = board.getSubBoard(60);

        //Then
        Assertions.assertEquals(expectedSet, resultSet);
    }
}
