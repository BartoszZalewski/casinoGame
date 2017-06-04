package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Core.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameRepository {

    private List<Game> games;

    public GameRepository() {
        games = new ArrayList<>();
        games.add(new FruitGame().create());
        games.add(new SpaceGame().create());
    }

    @RequestMapping(value = "/allGamesRepo")
    public List<Game> getAll() {
        return games;
    }


}
