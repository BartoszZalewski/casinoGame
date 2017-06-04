package com.casinoGame.casinoGame.Core;

import com.casinoGame.casinoGame.Validations.Validation;

import java.util.ArrayList;
import java.util.List;

public class BoardDefinition {
    public final int columns;
    public final int rows;
    public final List<LineDefinition> lines;
    public final List<SymbolLine> symbolLines;
    public final List<Validation> validations;
    public final List<Integer> jokers;
    public final List<SpecialSymbol> specialSymbols;

    public BoardDefinition(int rows, int columns, List<LineDefinition> lines, List<Validation> validations, List<SymbolLine> symbolLines, List<Integer> jokers, List<SpecialSymbol> specialSymbols) {
        this.columns = columns;
        this.rows = rows;
        this.lines = lines;
        this.symbolLines = symbolLines;
        this.jokers = jokers;
        this.validations = validations;
        this.specialSymbols = specialSymbols;
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



}
