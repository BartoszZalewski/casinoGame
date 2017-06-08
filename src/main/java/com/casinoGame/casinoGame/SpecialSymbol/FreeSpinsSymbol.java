package com.casinoGame.casinoGame.SpecialSymbol;

import com.casinoGame.casinoGame.Base.Event;
import com.casinoGame.casinoGame.Game.Logic.Logic;
import com.casinoGame.casinoGame.Line.Line;

public class FreeSpinsSymbol extends SpecialSymbol {
    private final int numberOf;
    private final int freeSpins;

    public FreeSpinsSymbol(int id, String value, int numberOf, int freeSpins) {
        super(id, value);
        this.numberOf = numberOf;
        this.freeSpins = freeSpins;
    }

    @Override
    public void call(int[][] board, Logic logic) {
        Line specialSymbolsLine = createLine(board, id);
        if(specialSymbolsLine.size() >= numberOf) {
            Event event = new Event("FreeSpinSymbolEvent", 0, specialSymbolsLine.inDefaultOrder(), specialSymbolsLine, name + ":" + freeSpins);
            logic.addEvent(event);
        }

    }
}
