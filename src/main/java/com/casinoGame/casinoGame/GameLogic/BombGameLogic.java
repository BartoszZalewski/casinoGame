package com.casinoGame.casinoGame.GameLogic;

import com.casinoGame.casinoGame.Core.BoardDefinition;
import com.casinoGame.casinoGame.Core.Event;
import com.casinoGame.casinoGame.Core.LineDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombGameLogic extends Logic {

    private List<Event> events;

    public BombGameLogic(BoardDefinition boardDefinition) {
        super(boardDefinition);
    }

    @Override
    public int[][] getValidBoard() {
        int [][]board = new int[boardDefinition.columns][boardDefinition.rows];
        for(int column = 0; column < boardDefinition.columns; column++){
            for(int row = 0; row < boardDefinition.rows; row++) {
                do {
                    int nextSymbol = new Random().nextInt(boardDefinition.getSymbols().size());
                    board[column][row] = nextSymbol;
                } while(!boardDefinition.valid(board));
            }
        }
        return board;
    }

    @Override
    public int beauty() {
        return 0;
    }

    @Override
    public int value(int[][] board) {
        events = new ArrayList<>();
        int sum = 0;

        List<LineDefinition> lines = boardDefinition.lines;
        for(LineDefinition lineDefinition : lines) {
            int matchedSize = lineDefinition.matchFromLeft(board, boardDefinition.jokers);
            if(matchedSize > 0) {
                int symbolId = lineDefinition.getFirstSymbolId(board);
                int lineValue = boardDefinition.getSymbolValue(symbolId, matchedSize);
                sum += lineValue;
                if(lineValue > 0) {
                    events.add(new Event("MatchedLine", lineValue, matchedSize, lineDefinition));
                }
            }
        }

        return sum;
    }


    @Override
    public List<Event> getEvents(int[][] board) {
        return events;
    }
}

