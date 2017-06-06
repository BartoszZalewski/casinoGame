package com.casinoGame.casinoGame.Core;

import java.util.List;

public class Event {
    final int points;
    final LineDefinition lineDefinition;
    final List<Integer> symbolsInLine;
    final String name;
    final String msg;

    public Event(String name, int points, List<Integer> symbolsInLine, LineDefinition lineDefinition, String msg) {
        this.points = points;
        this.lineDefinition = lineDefinition;
        this.symbolsInLine = symbolsInLine;
        this.name = name;
        this.msg = msg;
    }

}
