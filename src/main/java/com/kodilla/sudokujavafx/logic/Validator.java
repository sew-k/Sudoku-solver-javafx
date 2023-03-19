package com.kodilla.sudokujavafx.logic;

public enum Validator {
    INSTANCE;

    private Validator() {

    }

    public boolean checkFieldTextToInt(String text) throws NumberFormatException {
        if (Integer.parseInt(text) > 0 && Integer.parseInt(text) <= 9) {
            return true;
        } else {
            return false;
        }
    }

}
