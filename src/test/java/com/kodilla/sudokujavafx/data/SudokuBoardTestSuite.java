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
        Set<Integer> expectedSet1 = Set.of(2, 8, 3);
        Set<Integer> resultSet1 = board.getSubBoard(0);

        Set<Integer> expectedSet2 = Set.of(7, 8, 6);
        Set<Integer> resultSet2 = board.getSubBoard(60);

        //Then
        Assertions.assertEquals(expectedSet1, resultSet1);
        Assertions.assertEquals(expectedSet2, resultSet2);
    }
    @Test
    void testIsBoardSolvedIfSolved() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("092865741586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (IOException e) {

        }

        //When
        board.getElementFromBoard(0,0).calculateAvailableFieldValues(board);
        board.getElementFromBoard(0,0).setFieldValue(3);
//        System.out.println(board.getElementFromBoard(0,0).getAvailableFieldValues());
//        board.setValueElementFromBoard(3,0,0, false);

        //Then
        Assertions.assertTrue(board.isBoardSolved());
    }
    @Test
    void testIsBoardSolvedIfNotSolved() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("092865741586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (IOException e) {

        }

        //When & Then
        Assertions.assertFalse(board.isBoardSolved());
    }
    @Test
    void testIsBoardSolvedIfIncorrect() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("092865741586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (IOException e) {

        }
        board.getElementFromBoard(0,0).calculateAvailableFieldValues(board);
        board.getElementFromBoard(0,0).setFieldValue(9);
//        System.out.println(board.getElementFromBoard(0,0).getAvailableFieldValues());
//        board.setValueElementFromBoard(9,0,0, false);


        //When & Then
        Assertions.assertFalse(board.isBoardSolved());
    }

    @Test
    void testGetElementWithOneSolution() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("392865740586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (IOException e) {

        }
        //When
        SudokuElement expectedElement = board.getElementFromBoard(0,8);
        SudokuElement resultElement = board.getElementWithOneSolution();

        //Then
        Assertions.assertEquals(expectedElement, resultElement);
    }
    @Test
    void testUpdateBoardWithElement() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("390865740586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (IOException e) {

        }
        //When
//        SudokuElement elementToUpdateBoard = new SudokuElement(2,0,2);
//        board.updateBoardWithElement(elementToUpdateBoard);
//        SudokuElement expectedElement = elementToUpdateBoard;
        board.getElementFromBoard(0,2).setFieldValue(2);
        SudokuElement resultElement = board.getElementFromBoard(0,2);

        //Then
        //Assertions.assertEquals(expectedElement, resultElement);
        Assertions.assertEquals(2, resultElement.getFieldValue());
    }
    @Test
    void testGetElementWithMultipleSolutions() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("926571483351486279874923516582367194149258367763100825238700651617835942495612738");
        } catch (IOException e) {

        }
        //When
        SudokuElement expectedElement = board.getElementFromBoard(5,4);
        SudokuElement resultElement = board.getElementWithMultipleSolutions();

        //Then
        Assertions.assertEquals(expectedElement, resultElement);
        Assertions.assertEquals(2, resultElement.getAvailableFieldValues().size());
    }
}
