package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Core.*;
import com.casinoGame.casinoGame.GameLogic.FruitGameLogic;
import com.casinoGame.casinoGame.GameLogic.Logic;
import com.casinoGame.casinoGame.Session;
import com.casinoGame.casinoGame.SessionRepository;
import com.casinoGame.casinoGame.Validations.Validation;
import com.casinoGame.casinoGame.Validations.VerticalLineValidation;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
public class FruitGame extends Game{

    public final String NAME = "Fruit Game";
    public final String PAGE = "fruitGame";

    public FruitGame(){
    }

    public FruitGame(Logic logic) {
        super(logic);
    }

    public Game create() {
        List<LineDefinition> lines = Arrays.asList(
                new LineDefinition(0, Arrays.asList(
                        new Point(0,0),
                        new Point(1,0),
                        new Point(2,0))
                ),
                new LineDefinition(1, Arrays.asList(
                        new Point(0,1),
                        new Point(1,1),
                        new Point(2,1))
                ),
                new LineDefinition(2, Arrays.asList(
                        new Point(0,2),
                        new Point(1,2),
                        new Point(2,2))
                ),
                new LineDefinition(3, Arrays.asList(
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2))
                ),
                new LineDefinition(4, Arrays.asList(
                        new Point(0,2),
                        new Point(1,1),
                        new Point(2,0))
                )
        );

        List<SymbolLine> symbolLines = Arrays.asList(
                new SymbolLine(0, 3, 8),
                new SymbolLine(1, 3, 8),
                new SymbolLine(2, 3, 16),
                new SymbolLine(3, 3, 16),
                new SymbolLine(4, 3, 8),
                new SymbolLine(5, 3, 8),
                new SymbolLine(6, 3, 20),
                new SymbolLine(6, 3, 40),
                new SymbolLine(7, 3, 60)
        );


        List<Validation> validations = Collections.singletonList(
                new VerticalLineValidation(
                        new HashMap<Integer, Integer>(){
                            {
                                put(5,2);
                                put(6,1);
                                put(7,1);
                            }
                        }
                )
        );

        List<Integer> bets = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500);


        BoardDefinition boardDefinition = new BoardDefinition.Builder(3, 3)
                .withLines(lines)
                .withSymbols(symbolLines)
                .withValidations(validations)
                .withBets(bets)
                .build();


        System.out.println(new Gson().toJson(boardDefinition));

        return new Game(NAME, PAGE, new FruitGameLogic(boardDefinition));
    }

    @RequestMapping(value = "/" + PAGE +"/{name}")
    public String play(Model model, @PathVariable String name) {
        Session session = SessionRepository.getSession(name);
        session.setCurrentGame(create());
        Player player = session.getPlayer();
        model.addAttribute("username", name);
        model.addAttribute("credits", player.getCredits());
        model.addAttribute("bets", session.getGame().getBets());
        model.addAttribute("board", new Gson().toJson(session.getGame().getDefaultBoard()));
        return PAGE;
    }

}
