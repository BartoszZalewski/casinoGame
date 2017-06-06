package com.casinoGame.casinoGame.GameLogic;

import com.casinoGame.casinoGame.Core.BoardDefinition;
import com.casinoGame.casinoGame.Core.Event;
import com.casinoGame.casinoGame.Core.LineDefinition;
import java.util.ArrayList;
import java.util.List;

public class SpaceGameLogic extends Logic {

    public SpaceGameLogic(BoardDefinition boardDefinition) {
        super(boardDefinition);
    }

    @Override
    public int[][] getValidBoard() {
        int [][]board = new int[boardDefinition.columns][boardDefinition.rows];
        for(int column = 0; column < boardDefinition.columns; column++){
            for(int row = 0; row < boardDefinition.rows; row++) {
                do {
                    int nextSymbol = boardDefinition.getRandomSymbol();
                    board[column][row] = nextSymbol;
                } while(!boardDefinition.valid(board));
            }
        }
        return board;
    }


    @Override
    public int value(int[][] board, int bet) {
        events = new ArrayList<>();
        int sum = 0;

        List<LineDefinition> lines = boardDefinition.lines;
        for(LineDefinition lineDefinition : lines) {
            List<Integer> matchedLineIndexes = lineDefinition.match(board, boardDefinition.jokers);
            int matchedSize = matchedLineIndexes.size();
            if(matchedSize > 0) {
                int symbolId = lineDefinition.getFirstNoJokerSymbolId(board, boardDefinition.jokers);
                int lineValue = boardDefinition.getSymbolValue(symbolId, matchedSize) * bet;
                sum += lineValue;
                if(lineValue > 0) {
                    events.add(new Event("MatchedLine", lineValue, matchedLineIndexes, lineDefinition, ""));
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

