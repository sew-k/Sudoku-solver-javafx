package com.kodilla.sudokujavafx.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SudokuBoardTestSuite {
    @Test
    void testGetSubBoard() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        } catch (NumberFormatException e) {

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
    void testGetRowValues() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.getElementFromBoard(0,1).setFieldValue(4);
        board.getElementFromBoard(0,2).setFieldValue(5);
        board.getElementFromBoard(0,3).setFieldValue(6);
        board.getElementFromBoard(1,0).setFieldValue(7);
        board.getElementFromBoard(2,0).setFieldValue(8);

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
        board.getElementFromBoard(0,1).setFieldValue(4);
        board.getElementFromBoard(0,2).setFieldValue(5);
        board.getElementFromBoard(0,3).setFieldValue(6);
        board.getElementFromBoard(1,0).setFieldValue(7);
        board.getElementFromBoard(2,0).setFieldValue(8);

        //When
        List<Integer> resultList = board.getColValues(0);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(0,7,8,0,0,0,0,0,0));

        //Then
        Assertions.assertEquals(expectedList, resultList);
    }
    @Test
    void testDeepCopy() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.setName("test");
        board.getElementFromBoard(0,1).setFieldValue(4);
        board.getElementFromBoard(0,2).setFieldValue(5);
        board.getElementFromBoard(0,3).setFieldValue(6);
        board.getElementFromBoard(1,0).setFieldValue(7);
        board.getElementFromBoard(2,0).setFieldValue(8);

        //When
        SudokuBoard deepCopyOfBoard = new SudokuBoard();
        try {
            deepCopyOfBoard = board.deepCopy();
        } catch (CloneNotSupportedException e) {
        }

        //Then
        Assertions.assertEquals(board.getName(), deepCopyOfBoard.getName());
        Assertions.assertEquals(board.getSudokuBoardList(), deepCopyOfBoard.getSudokuBoardList());
    }

    @Test
    void testIsBoardCorrectWhenNot() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.calculateBoard();
        board.getElementFromBoard(0,0).setFieldValue(1);
        board.calculateBoard();
        board.getElementFromBoard(0,1).setFieldValue(1);

        //When

        boolean result = board.isBoardCorrect();

        //Then
        Assertions.assertFalse(result);
    }
    @Test
    void testIsBoardCorrectWhenCorrect() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.calculateBoard();
        board.getElementFromBoard(0,0).setFieldValue(1);
        board.calculateBoard();
        board.getElementFromBoard(0,1).setFieldValue(2);
        System.out.println(board);

        //When
        boolean result = board.isBoardCorrect();

        //Then
        Assertions.assertTrue(result);
    }
    @Test
    void testToSimpleString() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board.setName("test");
        board.getElementFromBoard(0,1).setFieldValue(4);
        board.getElementFromBoard(0,2).setFieldValue(5);
        board.getElementFromBoard(0,3).setFieldValue(6);
        board.getElementFromBoard(1,0).setFieldValue(7);
        board.getElementFromBoard(2,0).setFieldValue(8);

        //When
        String result = board.toSimpleString();
        String expected = "045600000700000000800000000000000000000000000000000000000000000000000000000000000";

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    void testIsBoardSolvedIfSolved() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("092865741586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (NumberFormatException e) {

        }

        //When
        board.getElementFromBoard(0,0).calculateAvailableFieldValues(board);
        board.getElementFromBoard(0,0).setFieldValue(3);

        //Then
        Assertions.assertTrue(board.isBoardSolved());
    }
    @Test
    void testIsBoardSolvedIfNotSolved() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("092865741586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (NumberFormatException e) {

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
        } catch (NumberFormatException e) {

        }
        board.getElementFromBoard(0,0).calculateAvailableFieldValues(board);
        board.getElementFromBoard(0,0).setFieldValue(9);

        //When & Then
        Assertions.assertFalse(board.isBoardSolved());
    }

    @Test
    void testGetElementWithOneSolution() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("392865740586741239147923658915276483823154967674398125238419576459637812761582394");
        } catch (NumberFormatException e) {

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
        } catch (NumberFormatException e) {

        }
        //When
        board.getElementFromBoard(0,2).setFieldValue(2);
        SudokuElement resultElement = board.getElementFromBoard(0,2);

        //Then
        Assertions.assertEquals(2, resultElement.getFieldValue());
    }
    @Test
    void testGetElementWithMultipleSolutions() {
        //Given
        SudokuBoard board = new SudokuBoard();
        try {
            board = board.setBoardFromString("926571483351486279874923516582367194149258367763100825238700651617835942495612738");
        } catch (NumberFormatException e) {

        }
        //When
        SudokuElement expectedElement = board.getElementFromBoard(5,4);
        SudokuElement resultElement = board.getElementWithMultipleSolutions();

        //Then
        Assertions.assertEquals(expectedElement, resultElement);
        Assertions.assertEquals(2, resultElement.getAvailableFieldValues().size());
    }
    @Test
    void testSaveAndLoadBoard() {
        //Given
        SudokuBoard boardToSave = new SudokuBoard();
        try {
            boardToSave = boardToSave.setBoardFromString("926571483351486279874923516582367194149258367763100825238700651617835942495612738");
        } catch (NumberFormatException e) {

        }
        File testFile = new File("testBoard.txt");

        //When
        try {
            boardToSave.saveBoard(testFile, boardToSave);
        } catch (IOException e) {

        }
        SudokuBoard loadedBoard = new SudokuBoard();
        loadedBoard = loadedBoard.loadBoard(testFile);

        //Then
        Assertions.assertEquals(boardToSave, loadedBoard);
        testFile.delete();
    }
    @Test
    void testSetBoardFromString() {
        //Given
        String boardInString = "926571483351486279874923516582367194149258367763100825238700651617835942495612738";
        SudokuBoard board = new SudokuBoard();

        //When
        try {
            board = board.setBoardFromString(boardInString);
        } catch (NumberFormatException e) {

        }

        //Then
        Assertions.assertEquals(boardInString, board.toSimpleString());
    }
    @Test
    void testSetBoardFromStringIncorrect() {
        //Given
        String boardInString = "92a571483351486279874923516582367194149258367763100825238700651617835942495612738";

        //When
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            SudokuBoard board = new SudokuBoard();
            board.setBoardFromString(boardInString);}
        );

        //Then
        Assertions.assertEquals("*** invalid element value at [2] ***", exception.getMessage());
    }
    @Test
    void testGetUnsolvedSudokuElements() {
        //Given


        //When

        //Then

    }
}
