package com.casinoGame.casinoGame.Game.Logic;

import com.casinoGame.casinoGame.Base.BoardDefinition;
import com.casinoGame.casinoGame.Base.Event;
import com.casinoGame.casinoGame.Base.Range;
import com.casinoGame.casinoGame.Base.Spin;
import com.casinoGame.casinoGame.Line.Line;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public abstract class Logic {
    protected final BoardDefinition boardDefinition;
    protected List<Event> events;
    protected Spin last;

    public Logic(BoardDefinition boardDefinition) {
        this.boardDefinition = boardDefinition;
    }

    public Spin getNextSpin(Range range, int bet) {
        int[][] board;
        int value;
        int counter = 0;
        int limit = getLimitValue();

        do {
            if(counter++ == limit){
                board = getValidLoserBoard();
                break;
            }

            board = getValidBoard();
            value = value(board);

        } while(checkValueInRange(value, range));

        int lose = loseValue(bet);
        multiplyEvents(bet);

        Spin spin = new Spin(board, getEvents(), getEventsValue(events), lose, bet);

        setLastSpin(spin);
        log(spin, range, counter, limit);
        return spin;
    }

    protected abstract int value(int[][] board);

    protected int getLimitValue(){
        return 5000;
    }

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

    protected int[][] getValidLoserBoard(){
        int[][] board;
        int value;
        do{
            board = getValidBoard();
            value = value(board);
        } while(value != 0);
        return board;
    }

    protected boolean checkValueInRange(int value, Range range){
        return !range.contains(value);
    }

    protected List<Event> getLinesValueEvents(int [][] board) {
        events = new ArrayList<>();
        List<Line> lines = boardDefinition.lines;
        for(Line line : lines) {
            List<Integer> matchedLineIndexes = line.match(board, boardDefinition.jokers);
            int matchedSize = matchedLineIndexes.size();
            if(matchedSize > 0) {
                int symbolId = line.getFirstNoJokerSymbolId(board, boardDefinition.jokers);
                int lineValue = boardDefinition.getSymbolValue(symbolId, matchedSize);
                if(lineValue > 0) {
                    events.add(new Event("MatchedLine", lineValue, matchedLineIndexes, line, ""));
                }
            }
        }
        return events;
    }

    protected void multiplyEvents(int bet){
        for(Event event : events) {
            event.multiplyPoints(bet);
        }
    }

    public List<Event> getEvents() {
        return events;
    }

    protected void setLastSpin(Spin spin){
        last = spin;
    }

    public BoardDefinition getBoardDefinition() {
        return boardDefinition;
    }

    protected int getEventsValue(List<Event> events) {
        int sum = 0;
        try {
            for (Event event : events) {
                sum += event.getPoints();
            }
        }catch(Exception e){
            System.out.println("Wyjebało sie :(");
            System.out.println(events);
        }
        return sum;
    }

    protected int loseValue(int bet){
        return bet;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    private void log(Spin spin, Range range, int counter, int limit){
        if(counter == limit){
            System.out.println(range.toString() + "  ---->>BLAD  " + new Gson().toJson(spin));
        } else {
            System.out.println(new Gson().toJson(spin));
        }
    }

}
