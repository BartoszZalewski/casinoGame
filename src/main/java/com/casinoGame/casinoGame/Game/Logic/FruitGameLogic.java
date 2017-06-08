package com.casinoGame.casinoGame.Game.Logic;

import com.casinoGame.casinoGame.Base.BoardDefinition;

public class FruitGameLogic extends Logic {

    public FruitGameLogic(BoardDefinition boardDefinition) {
        super(boardDefinition);
    }

    @Override
    public int value(int[][] board) {
        events = getLinesValueEvents(board);
        return getEventsValue();
    }

}

