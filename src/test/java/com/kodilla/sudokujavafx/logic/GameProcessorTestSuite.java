package com.kodilla.sudokujavafx.logic;

import com.kodilla.sudokujavafx.data.SudokuBoard;
import com.kodilla.sudokujavafx.data.SudokuElement;
import com.kodilla.sudokujavafx.data.SudokuMove;
import com.kodilla.sudokujavafx.logic.GameProcessor;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameProcessorTestSuite {

    @Test
    void testSetAndGetOriginalBoard() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        GameProcessor.INSTANCE.setOriginalBoard(board);

        //When
        SudokuBoard resultBoard = GameProcessor.INSTANCE.getOriginalBoard();

        //Then
        Assertions.assertEquals(board,resultBoard);
    }
    @Test
    void testAddMoveToBackTrack() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");

        //When
        GameProcessor.INSTANCE.addMoveToBackTrack(new SudokuMove(board, 0, 0, 1));
        SudokuBoard resultBoard = GameProcessor.INSTANCE.getBackTrack().pollLast().getBoard();

        //Then
        Assertions.assertEquals(board,resultBoard);
    }
    @Test
    void testSolveRandomSudokuElement() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");
        SudokuBoard copyOfBoard = GameProcessor.INSTANCE.getCopyOfBoard(board);
        GameProcessor.INSTANCE.setBoard(board);

        //When
        GameProcessor.INSTANCE.solveRandomSudokuElement(board);

        //Then
        Assertions.assertNotEquals(copyOfBoard,board);
        Assertions.assertTrue(copyOfBoard.isBoardCorrect());
        Assertions.assertTrue(board.isBoardCorrect());
        Assertions.assertEquals(copyOfBoard.getUnsolvedSudokuElements().size(), board.getUnsolvedSudokuElements().size() + 1);
    }

    @Test
    void testGetCopyOfBoard() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");

        //When
        SudokuBoard copyOfBoard = GameProcessor.INSTANCE.getCopyOfBoard(board);

        //Then
        Assertions.assertEquals(copyOfBoard,board);
    }

    @Test
    void testSolveBoard() {
        //Given
        SudokuBoard board = new SudokuBoard();
        board = board.setBoardFromString("020501090800203006030060070001000600540000019002000700090030080200804007010907060");

        //When
        SudokuBoard solvedBoard = GameProcessor.INSTANCE.solveBoard(board);
        SudokuBoard expected = new SudokuBoard();
        expected = expected.setBoardFromString("426571398857293146139468275971385624543726819682149753794632581265814937318957462");

        //Then
        Assertions.assertEquals(expected, solvedBoard);
    }
}
