package com.casinoGame.casinoGame.GameLogic;

import com.casinoGame.casinoGame.Core.BoardDefinition;
import com.casinoGame.casinoGame.Core.Event;

import java.util.List;

public abstract class Logic {
    final BoardDefinition boardDefinition;

    public Logic(BoardDefinition boardDefinition) {
        this.boardDefinition = boardDefinition;
    }

    public abstract int[][] getValidBoard();

    public abstract int beauty();

    public abstract  int value(int[][] board);

    public abstract List<Event> getEvents(int[][] board);

}
