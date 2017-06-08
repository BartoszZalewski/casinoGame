package com.casinoGame.casinoGame.Base;


import java.util.Arrays;
import java.util.List;

public class Spin {
    private final int [][] board;
    private List<Event> events;
    public final int win;
    public final int lose;
    public final int bet;

    public Spin(int[][] board, List<Event> events, int win, int lose, int bet) {
        this.board = Arrays.copyOf(board,board.length);
        this.events = events;
        this.win = win;
        this.lose = lose;
        this.bet = bet;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public int getWin(){
        return win;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("{");
        stringBuilder.append(Arrays.deepToString(board)).append(",");
        stringBuilder.append(events).append(",");
        stringBuilder.append(win).append(",");
        stringBuilder.append(lose);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public List<Event> getEvents() {
        return events;
    }
}
