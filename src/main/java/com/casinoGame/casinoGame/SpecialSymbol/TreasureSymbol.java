package com.casinoGame.casinoGame.SpecialSymbol;

import com.casinoGame.casinoGame.Base.Event;
import com.casinoGame.casinoGame.Base.Range;
import com.casinoGame.casinoGame.Game.Logic.Logic;
import com.casinoGame.casinoGame.Line.Line;
import com.casinoGame.casinoGame.Line.Point;

import java.util.ArrayList;
import java.util.List;

public class TreasureSymbol extends SpecialSymbol {
    private final int numberOf;
    private final Range priceRange;

    public TreasureSymbol(int id, String value, int numberOf, Range priceRange) {
        super(id, value);
        this.numberOf = numberOf;
        this.priceRange = priceRange;
    }

    @Override
    public void call(int[][] board, Logic logic) {
        int points = priceRange.random();
        Line specialSymbolsLine = createLine(board, id);
        if(specialSymbolsLine.size() >= numberOf) {
            Event event = new Event("TreasureSymbolEvent", points, specialSymbolsLine.inDefaultOrder(), specialSymbolsLine, name);
            logic.addEvent(event);
        }

    }

}
