package com.casinoGame.casinoGame.Base;

import java.util.Random;

public class Range {
    public final int from;
    public final int to;

    public Range(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public boolean contains(int value) {
        return from <= value && value <= to;
    }

    @Override
    public String toString() {
        return "[" + from+", "+ to +"]";
    }

    public int random() {
        return new Random().nextInt(to - from) + from;
    }
}
