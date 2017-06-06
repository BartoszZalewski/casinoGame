package com.casinoGame.casinoGame.GameLogic;

import com.casinoGame.casinoGame.Core.BoardDefinition;
import com.casinoGame.casinoGame.Core.Event;

import java.util.List;

public abstract class Logic {
    final BoardDefinition boardDefinition;
    List<Event> events;

    public Logic(BoardDefinition boardDefinition) {
        this.boardDefinition = boardDefinition;
    }

    public abstract int[][] getValidBoard();

    public abstract  int value(int[][] board, int bet);

    public abstract List<Event> getEvents(int[][] board);

    public BoardDefinition getBoardDefinition() {
        return boardDefinition;
    }

}
