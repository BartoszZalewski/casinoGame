package com.casinoGame.casinoGame.Core;

import com.casinoGame.casinoGame.Validations.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardDefinition {
    public final int columns;
    public final int rows;
    public final List<LineDefinition> lines;
    public final List<SymbolLine> symbolLines;
    public final List<Validation> validations;
    public final List<Integer> jokers;
    public final List<SpecialSymbol> specialSymbols;
    public final List<Integer> bets;

    private BoardDefinition(Builder builder) {
        this.columns = builder.columns;
        this.rows = builder.rows;
        this.lines = builder.lines;
        this.symbolLines = builder.symbolLines;
        this.jokers = builder.jokers;
        this.validations = builder.validations;
        this.specialSymbols = builder.specialSymbols;
        this.bets = builder.bets;
    }

    public List<Integer> getSymbols() {
        List<Integer> symbols = new ArrayList<>();
        symbolLines.stream().filter(symbolLine -> !symbols.contains(symbolLine.id)).forEach(symbolLine -> symbols.add(symbolLine.id));
        return symbols;
    }

    public int getSymbolValue(int id, int length) {
        for(SymbolLine symbolLine : symbolLines) {
            if(symbolLine.match(id, length)){
                return symbolLine.value;
            }
        }
        return 0;
    }

    public boolean valid(int[][] board) {
        for(Validation validation : validations) {
            if(!validation.valid(board)) {
                return false;
            }
        }
        return true;
    }

    public int getRandomSymbol() {
        List<Integer> symbols = getSymbols();
        int index = new Random().nextInt(symbols.size());
        return symbols.get(index);
    }

    public static class Builder {
        int columns;
        int rows;
        List<LineDefinition> lines = new ArrayList<>();
        List<SymbolLine> symbolLines = new ArrayList<>();
        List<Validation> validations = new ArrayList<>();
        List<Integer> jokers = new ArrayList<>();
        List<SpecialSymbol> specialSymbols = new ArrayList<>();
        List<Integer> bets = new ArrayList<>();

        public Builder(int rows, int columns) {
            this.columns = columns;
            this.rows = rows;
        }

        public Builder withLines(List<LineDefinition> lines) {
            this.lines = lines;
            return this;
        }

        public Builder withValidations(List<Validation> validations) {
            this.validations = validations;
            return this;
        }

        public Builder withSymbols(List<SymbolLine> symbolLines) {
            this.symbolLines = symbolLines;
            return this;
        }

        public Builder withJokers(List<Integer> jokers) {
            this.jokers = jokers;
            return this;
        }

        public Builder withSpecialSymbols(List<SpecialSymbol> specialSymbols) {
            this.specialSymbols = specialSymbols;
            return this;
        }

        public Builder withBets(List<Integer> bets) {
            this.bets = bets;
            return this;
        }

        public BoardDefinition build(){
            return new BoardDefinition(this);
        }
    }

}
