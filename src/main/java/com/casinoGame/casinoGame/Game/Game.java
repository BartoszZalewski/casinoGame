package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Base.Range;
import com.casinoGame.casinoGame.Base.Spin;
import com.casinoGame.casinoGame.Game.Logic.Logic;
import com.google.gson.Gson;

public abstract class Game {
    public String NAME;
    public String PAGE;
    public Logic logic;

    public Game(){
    }

    public Game(Logic logic){
        this.logic = logic;
    }

    public abstract Game create();


    public Spin getNextSpin(Range range, int bet){
        return logic.getNextSpin(range, bet);
    }

    public String getBets(){
        return new Gson().toJson(logic.getBoardDefinition().bets);
    }

    public int[][] getDefaultBoard() {
        return logic.getValidBoard();
    }

}
