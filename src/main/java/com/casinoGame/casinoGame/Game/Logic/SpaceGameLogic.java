package com.casinoGame.casinoGame.Game.Logic;

import com.casinoGame.casinoGame.Base.BoardDefinition;
import com.casinoGame.casinoGame.SpecialSymbol.SpecialSymbol;

public class SpaceGameLogic extends Logic {

    public SpaceGameLogic(BoardDefinition boardDefinition) {
        super(boardDefinition);
    }

    @Override
    public int value(int[][] board) {
        events = getLinesValueEvents(board);

        for(SpecialSymbol specialSymbol : boardDefinition.specialSymbols) {
            specialSymbol.call(board, this);
        }

        return getEventsValue(events);
    }

    @Override
    protected int getLimitValue() {
        return super.getLimitValue() * 10;
    }
}

