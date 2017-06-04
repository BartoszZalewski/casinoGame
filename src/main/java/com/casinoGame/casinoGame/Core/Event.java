package com.casinoGame.casinoGame.Core;

public class Event {
    final int points;
    final LineDefinition lineDefinition;
    final int symbolsInLine;
    final String name;

    public Event(String name, int points, int symbolsInLine, LineDefinition lineDefinition) {
        this.points = points;
        this.lineDefinition = lineDefinition;
        this.symbolsInLine = symbolsInLine;
        this.name = name;
    }

}
