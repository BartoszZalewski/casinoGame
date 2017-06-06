package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Core.*;
import com.casinoGame.casinoGame.GameLogic.BombGameLogic;
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
public class BombGame extends Game{

    public final String NAME = "Bomb Game";
    public final String PAGE = "bombGame";

    public BombGame(){
    }

    public BombGame(Logic logic) {
        super(logic);
    }

    public Game create() {
        List<LineDefinition> lines = Arrays.asList(
                new LineDefinition(0, Arrays.asList(
                        new Point(0,0),
                        new Point(1,0),
                        new Point(2,0),
                        new Point(3,0),
                        new Point(4,0))
                ),
                new LineDefinition(1, Arrays.asList(
                        new Point(0,1),
                        new Point(1,1),
                        new Point(2,1),
                        new Point(3,1),
                        new Point(4,1))
                ),
                new LineDefinition(2, Arrays.asList(
                        new Point(0,2),
                        new Point(1,2),
                        new Point(2,2),
                        new Point(3,2),
                        new Point(4,2))
                ),
                new LineDefinition(3, Arrays.asList(
                        new Point(0,3),
                        new Point(1,3),
                        new Point(2,3),
                        new Point(3,3),
                        new Point(4,3))
                ),
                new LineDefinition(4, Arrays.asList(
                        new Point(0,4),
                        new Point(1,4),
                        new Point(2,4),
                        new Point(3,4),
                        new Point(4,4))
                ),
                new LineDefinition(5, Arrays.asList(
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,3),
                        new Point(4,4))
                ),
                new LineDefinition(6, Arrays.asList(
                        new Point(0,4),
                        new Point(1,3),
                        new Point(2,2),
                        new Point(3,1),
                        new Point(4,0))
                ),
                new LineDefinition(7, Arrays.asList(
                    new Point(0,0),
                    new Point(1,4),
                    new Point(2,0),
                    new Point(3,4),
                    new Point(4,0))
                ),
                new LineDefinition(8, Arrays.asList(
                        new Point(0,4),
                        new Point(1,0),
                        new Point(2,4),
                        new Point(3,0),
                        new Point(4,4))
                )
        );

        List<SymbolLine> symbolLines = Arrays.asList(
                new SymbolLine(5, 2, 1),
                new SymbolLine(5, 3, 2),
                new SymbolLine(5, 4, 3),
                new SymbolLine(5, 5, 4),
                new SymbolLine(6, 2, 3),
                new SymbolLine(6, 3, 4),
                new SymbolLine(6, 4, 5),
                new SymbolLine(6, 5, 6),
                new SymbolLine(7, 2, 5),
                new SymbolLine(7, 3, 6),
                new SymbolLine(7, 4, 7),
                new SymbolLine(7, 5, 8),
                new SymbolLine(8, 2, 10),
                new SymbolLine(8, 3, 20),
                new SymbolLine(8, 4, 50),
                new SymbolLine(8, 5, 100),
                new SymbolLine(9, 2, 20),
                new SymbolLine(9, 3, 50),
                new SymbolLine(9, 4, 100),
                new SymbolLine(9, 5, 200),
                new SymbolLine(10, 2, 20),
                new SymbolLine(10, 3, 50),
                new SymbolLine(10, 4, 100),
                new SymbolLine(10, 5, 200),
                new SymbolLine(11, 2, 50),
                new SymbolLine(11, 3, 100),
                new SymbolLine(11, 4, 200),
                new SymbolLine(11, 5, 300),
                new SymbolLine(12, 2, 50),
                new SymbolLine(12, 3, 100),
                new SymbolLine(12, 4, 200),
                new SymbolLine(12, 5, 300),
                new SymbolLine(13, 2, 200),
                new SymbolLine(13, 3, 600),
                new SymbolLine(13, 4, 800),
                new SymbolLine(13, 5, 1000)
        );

        List<Integer> jokers = Collections.singletonList(
                13
        );

        List<Validation> validations = Collections.singletonList(
                new VerticalLineValidation(
                        new HashMap<Integer, Integer>(){
                            {
                                put(10,2);
                                put(11,2);
                                put(12,1);
                                put(13,1);
                            }
                        }
                )
        );

        List<Integer> bets = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500);

        BoardDefinition boardDefinition = new BoardDefinition.Builder(5, 5)
                .withLines(lines)
                .withJokers(jokers)
                .withSymbols(symbolLines)
                .withValidations(validations)
                .withBets(bets)
                .build();


        return new Game(NAME, PAGE, new BombGameLogic(boardDefinition));
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
        return "fruitGame";
    }

}
