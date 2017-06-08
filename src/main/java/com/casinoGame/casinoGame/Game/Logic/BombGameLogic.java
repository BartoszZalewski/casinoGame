package com.casinoGame.casinoGame.Game.Logic;

import com.casinoGame.casinoGame.Base.BoardDefinition;
import com.casinoGame.casinoGame.Base.Event;
import com.casinoGame.casinoGame.Line.Line;
import com.casinoGame.casinoGame.SpecialSymbol.SpecialSymbol;
import com.casinoGame.casinoGame.Validations.Validation;

import java.util.ArrayList;

public class BombGameLogic extends Logic {

    public BombGameLogic(BoardDefinition boardDefinition) {
        super(boardDefinition);
    }

    @Override
    public int[][] getValidBoard() {
        if (last != null && last.lose == 0) {
            int[][] board = new int[boardDefinition.columns][boardDefinition.rows];
            for (int column = 0; column < boardDefinition.columns; column++) {
                for (int row = 0; row < boardDefinition.rows; row++) {
                    int nextSymbol;
                    do {
                        nextSymbol = boardDefinition.getRandomSymbol();
                        board[column][row] = nextSymbol;
                    } while (!boardDefinition.valid(board) || isSpecialSymbol(nextSymbol));
                }
            }
            return board;
        }
        return super.getValidBoard();
    }

    private boolean isSpecialSymbol(int id) {
        for(SpecialSymbol specialSymbol : boardDefinition.specialSymbols) {
            if(specialSymbol.id == id){
                return true;
            }
        }
        return false;
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
    protected int loseValue(int bet) {
        if(last != null) {
            for (Event event : last.getEvents()) {
                if (event.msg.contains("FreeSpin")) {
                    String[] values = event.msg.split(":");
                    int freeSpins = Integer.parseInt(values[1]) - 1;
                    if (freeSpins > 0) {
                        Event nextFreeSpin = new Event(event.name, 0, new ArrayList<>(), new Line(0, new ArrayList<>()), values[0] + ":" + freeSpins);
                        events.add(nextFreeSpin);
                    }
                    return 0;
                }
            }
        }

        return bet;
    }
}

