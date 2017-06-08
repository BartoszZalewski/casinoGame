package com.casinoGame.casinoGame.SpecialSymbol;

import com.casinoGame.casinoGame.Game.Logic.Logic;
import com.casinoGame.casinoGame.Line.Line;
import com.casinoGame.casinoGame.Line.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class SpecialSymbol {
    public final int id;
    public final String name;

    public SpecialSymbol(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void call(int[][] board, Logic logic);

    protected Line createLine(int[][] board, int id) {
        List<Point> points = new ArrayList<>();
        for(int col = 0; col < board.length; col++) {
            for(int row = 0; row < board[col].length; row++) {
                int currId = board[col][row];
                if(currId == id){
                    Point point = new Point(col, row);
                    points.add(point);
                }
            }
        }
        return new Line(0, points);
    }

}
