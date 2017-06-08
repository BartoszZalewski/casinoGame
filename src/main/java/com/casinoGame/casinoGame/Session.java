package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Game.Game;
import com.casinoGame.casinoGame.Base.Player;

public class Session {

    private Player player;
    private Game currentGame;

    public Session(){

    }

    public Session(Player player){
        this.player = player;
    }

    public void setCurrentGame(Game game){
        this.currentGame = game;
    }

    public Player getPlayer(){
        return player;
    }

    public Game getGame() {
        return currentGame;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        return player != null ? player.equals(session.player) : session.player == null;
    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }
}
