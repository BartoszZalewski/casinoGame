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
    protected boolean valid(int[][] board) {
        for (Validation validation : boardDefinition.getValidations()) {
            if (!validation.valid(board, last, boardDefinition.getSpecialSymbols())) {
                return false;
            }
        }
        return true;

    }

    @Override
    public int value(int[][] board) {
        events = getLinesValueEvents(board);

        for(SpecialSymbol specialSymbol : boardDefinition.getSpecialSymbols()) {
            specialSymbol.call(board, this);
        }

        return getEventsValue();
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

