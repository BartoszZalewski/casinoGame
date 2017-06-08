package com.casinoGame.casinoGame.Validations;

import com.casinoGame.casinoGame.Base.Spin;
import com.casinoGame.casinoGame.SpecialSymbol.SpecialSymbol;
import java.util.List;

public class FreeSpecialSymbolsValidation extends Validation {
    private final int value;

    public FreeSpecialSymbolsValidation() {
        this.value = 0;
    }

    @Override
    public boolean valid(int[][] board, Object... objects) {
        Spin last = (Spin) objects[0];
        if (last != null && last.lose == 0 && objects[1] != null) {
            for(int column = 0; column < board.length; column++) {
                for (int row = 0; row < board[column].length; row++) {
                    int nextSymbol = board[column][row];
                    if(isSpecialSymbol(nextSymbol, (List<SpecialSymbol>) objects[1])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isSpecialSymbol(int id, List<SpecialSymbol> specialSymbols) {
        for(SpecialSymbol specialSymbol : specialSymbols) {
            if(specialSymbol.id == id){
                return true;
            }
        }
        return false;
    }

    public int getValue() {
        return value;
    }
}
