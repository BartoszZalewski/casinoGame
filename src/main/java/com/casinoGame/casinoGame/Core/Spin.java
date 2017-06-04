package com.casinoGame.casinoGame.Core;



import java.util.Arrays;
import java.util.List;

public class Spin {
    private final int [][] board;
    private final List<Event> events;
    public final int win;
    public final int lose;

    public Spin(int[][] board, List<Event> events, int win, int lose) {
        this.board = Arrays.copyOf(board,board.length);
        this.events = events;
        this.win = win;
        this.lose = lose;
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

}
