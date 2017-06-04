package com.casinoGame.casinoGame.Core;

public class SymbolLine {
    public final int id;
    private final int length;
    final int value;

    public SymbolLine(int id, int length, int value) {
        this.id = id;
        this.length = length;
        this.value = value;
    }

    public boolean match(int id, int length) {
        return this.id == id && this.length == length;
    }

}
