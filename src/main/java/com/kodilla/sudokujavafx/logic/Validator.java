package com.kodilla.sudokujavafx.logic;

import java.util.Set;

public enum Validator {
    INSTANCE;

    private final Set<Character> availableCharacterForSudoku = Set.of('0','1','2','3','4','5','6','7','8','9');

    private Validator() {

    }

    public boolean checkFieldTextToInt(String text) throws NumberFormatException {
        if (Integer.parseInt(text) > 0 && Integer.parseInt(text) <= 9) {
            return true;
        } else {
            return false;
        }
    }

    public Set<Character> getAvailableCharacterForSudoku() {
        return availableCharacterForSudoku;
    }
}
