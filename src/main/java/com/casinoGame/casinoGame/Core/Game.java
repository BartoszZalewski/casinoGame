package com.casinoGame.casinoGame.Core;

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


    public Spin getNextSpin(int value, int bet){
        int limit = 5000;
        int[][] board;
        int counter = 0;
        do {
            board = logic.getValidBoard();
            counter++;
        } while(logic.value(board) != value && counter < limit);

        Spin spin = new Spin(board, logic.getEvents(board), logic.value(board), bet);
        if(counter == limit){
            System.out.println(value + "  ---->>BLAD  " + new Gson().toJson(spin));
        } else {
            System.out.println(new Gson().toJson(spin));
        }

        return spin;
    }

}
