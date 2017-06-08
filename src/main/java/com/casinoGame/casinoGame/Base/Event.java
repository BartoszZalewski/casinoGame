package com.casinoGame.casinoGame.Base;

import com.casinoGame.casinoGame.Line.Line;
import java.util.List;

public class Event {
    private int points;
    public final Line line;
    public final List<Integer> symbolsInLine;
    public final String name;
    public final String msg;

    public Event(String name, int points, List<Integer> symbolsInLine, Line line, String msg) {
        this.points = points;
        this.line = line;
        this.symbolsInLine = symbolsInLine;
        this.name = name;
        this.msg = msg;
    }

    public int getPoints() {
        return points;
    }

    public void multiplyPoints(int value) {
        this.points *= value;
    }

}
