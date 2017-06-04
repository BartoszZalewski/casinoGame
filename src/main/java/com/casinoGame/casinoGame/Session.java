package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Core.Game;
import com.casinoGame.casinoGame.Core.Player;
import com.casinoGame.casinoGame.Core.Spin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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

}
