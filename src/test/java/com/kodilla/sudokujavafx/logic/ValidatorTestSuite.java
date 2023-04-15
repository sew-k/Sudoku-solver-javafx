package com.kodilla.sudokujavafx.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ValidatorTestSuite {
    @Test
    void testGetAvailableCharacterForSudoku() {
        //Given & When
        Set<Character> charactersSet = Validator.INSTANCE.getAvailableCharacterForSudoku();
        Set<Character> expectedSet = Set.of('0','1','2','3','4','5','6','7','8','9');

        //Then
        Assertions.assertEquals(expectedSet, charactersSet);
    }
    @Test
    void testCheckFieldTextToIntCorrect() {
        //Given
        String text = "1";

        //When
        boolean result = Validator.INSTANCE.checkFieldTextToInt(text);

        //Then
        Assertions.assertTrue(result);
    }
    @Test
    void testCheckFieldTextToIntIncorrect() {
        //Given
        String text = "a";

        //When
        boolean result = Validator.INSTANCE.checkFieldTextToInt(text);

        //Then
        Assertions.assertFalse(result);
    }
}
