package com.casinoGame.casinoGame.Base;

import com.casinoGame.casinoGame.Line.Line;
import com.casinoGame.casinoGame.SpecialSymbol.SpecialSymbol;
import com.casinoGame.casinoGame.Validations.Validation;

import java.util.*;

public class BoardDefinition {
    public final int columns;
    public final int rows;
    public final List<Line> lines;
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
        Set<Integer> symbols = new HashSet<>();

        for(SymbolLine symbolLine : symbolLines) {
            symbols.add(symbolLine.id);
        }

        symbols.addAll(jokers);

        for(SpecialSymbol specialSymbol : specialSymbols) {
            symbols.add(specialSymbol.id);
        }

        return new ArrayList<>(symbols);
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
        List<Line> lines = new ArrayList<>();
        List<SymbolLine> symbolLines = new ArrayList<>();
        List<Validation> validations = new ArrayList<>();
        List<Integer> jokers = new ArrayList<>();
        List<SpecialSymbol> specialSymbols = new ArrayList<>();
        List<Integer> bets = new ArrayList<>();

        public Builder(int rows, int columns) {
            this.columns = columns;
            this.rows = rows;
        }

        public Builder withLines(List<Line> lines) {
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
