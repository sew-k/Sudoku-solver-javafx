package com.kodilla.sudokujavafx;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import org.junit.jupiter.api.*;

import java.util.*;

public class SudokuJavaFxTestSuite {

@Nested
@DisplayName("SudokuBoardTests")
class SudokuBoardTests {
    @Test
    void testGetSubBoardValues() {

    }

    @Test
    void testGetAvailableFieldValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.setValueElementFromBoard(4,0,1);
        board.setValueElementFromBoard(5,0,2);
        board.setValueElementFromBoard(6,0,3);
        board.setValueElementFromBoard(7,1,0);
        board.setValueElementFromBoard(8,2,0);

        //When
        Set<Integer> resultSet = board.getElementFromBoard(0,0).getAvailableFieldValues(board);
        Set<Integer> expectedSet = new HashSet<>(Set.of(1,2,3,9));

        //Then
        Assertions.assertEquals(expectedSet, resultSet);
    }
    @Test
    void testGetRowValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.setValueElementFromBoard(4,0,1);
        board.setValueElementFromBoard(5,0,2);
        board.setValueElementFromBoard(6,0,3);
        board.setValueElementFromBoard(7,1,0);
        board.setValueElementFromBoard(8,2,0);
        System.out.println(board.getColValues(0));
        System.out.println(board.getRowValues(0));

        //When
        List<Integer> resultList = board.getRowValues(0);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(0,4,5,6,0,0,0,0,0));

        //Then
        Assertions.assertEquals(expectedList, resultList);
    }
    @Test
    void testGetColValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.setValueElementFromBoard(4,0,1);
        board.setValueElementFromBoard(5,0,2);
        board.setValueElementFromBoard(6,0,3);
        board.setValueElementFromBoard(7,1,0);
        board.setValueElementFromBoard(8,2,0);
        System.out.println(board.getColValues(0));
        System.out.println(board.getRowValues(0));

        //When
        List<Integer> resultList = board.getColValues(0);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(0,7,8,0,0,0,0,0,0));

        //Then
        Assertions.assertEquals(expectedList, resultList);
    }
}

}
