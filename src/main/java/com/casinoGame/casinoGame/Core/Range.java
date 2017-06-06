package com.casinoGame.casinoGame.Core;


public class Range {
    public final double from;
    public final double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public boolean contains(double value) {
        return from <= value && value <= to;
    }

    public Range multiply(double value) {
        return new Range(value * from, value * to);
    }

    @Override
    public String toString() {
        return "[" + from+", "+ to +"]";
    }
}
