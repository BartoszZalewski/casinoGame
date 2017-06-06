package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Core.Range;
import com.casinoGame.casinoGame.Core.Spin;
import com.casinoGame.casinoGame.GameLogic.Logic;
import com.google.gson.Gson;

public class Game {
    public String name;
    public String page;
    public Logic logic;

    public Game(){

    }

    public Game(Logic logic){
        this.logic = logic;
    }

    public Game(String name, String page, Logic logic){
        this.name = name;
        this.page = page;
        this.logic = logic;
    }


    public Spin getNextSpin(Range range, int bet){
        int limit = 5000;
        int[][] board;
        range = range.multiply(bet);
        int counter = 0;
        do {
            board = logic.getValidBoard();
            counter++;
        } while(!range.contains(logic.value(board, bet)) && counter < limit);

        Spin spin = new Spin(board, logic.getEvents(board), logic.value(board, bet), bet, bet);
        if(counter == limit){
            System.out.println(range.toString() + "  ---->>BLAD  " + new Gson().toJson(spin));
        } else {
            System.out.println(new Gson().toJson(spin));
        }

        return spin;
    }

    public String getBets(){
        return new Gson().toJson(logic.getBoardDefinition().bets);
    }

    public int[][] getDefaultBoard() {
        return logic.getValidBoard();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        return page != null ? page.equals(game.page) : game.page == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (page != null ? page.hashCode() : 0);
        return result;
    }
}
