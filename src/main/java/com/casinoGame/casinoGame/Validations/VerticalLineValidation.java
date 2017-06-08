package com.casinoGame.casinoGame.Validations;

import java.util.HashMap;

public class VerticalLineValidation extends Validation{
    private final HashMap<Integer, Integer> symbolCounts;

    public VerticalLineValidation(HashMap<Integer, Integer> symbolCounts) {
        this.symbolCounts = symbolCounts;
    }

    @Override
    public boolean valid(int[][] board, Object... objects) {

        for(Integer symbolId : symbolCounts.keySet()) {
            int maxExpectedValue = symbolCounts.get(symbolId);
            for(int column = 0; column < board.length; column++) {
                if (symbolCountInVertical(board, column, symbolId) > maxExpectedValue) {
                    return false;
                }
            }
        }

        return true;
    }

    private int symbolCountInVertical(int [][]board, int column, int symbolId) {
        int counter = 0;

        for(int row = 0; row < board[column].length; row++) {
            if(board[column][row] == symbolId) {
                counter++;
            }
        }
        return counter;
    }

    public HashMap<Integer, Integer> getSymbolCounts() {
        return symbolCounts;
    }

}
