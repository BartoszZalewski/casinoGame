package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Core.*;
import com.casinoGame.casinoGame.GameLogic.FruitGameLogic;
import com.casinoGame.casinoGame.GameLogic.Logic;
import com.casinoGame.casinoGame.Session;
import com.casinoGame.casinoGame.SessionRepository;
import com.casinoGame.casinoGame.Validations.Validation;
import com.casinoGame.casinoGame.Validations.VerticalLineValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
public class SpaceGame extends Game{

    public final String NAME = "Space Game";
    public final String PAGE = "spaceGame";

    public SpaceGame(){
    }

    public SpaceGame(Logic logic) {
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
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,1),
                        new Point(4,0))
                ),
                new LineDefinition(4, Arrays.asList(
                        new Point(0,2),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,1),
                        new Point(4,2))
                )
        );

        List<SymbolLine> symbolLines = Arrays.asList(
                new SymbolLine(0, 2, 1),
                new SymbolLine(0, 3, 2),
                new SymbolLine(0, 4, 3),
                new SymbolLine(0, 5, 4),
                new SymbolLine(1, 2, 3),
                new SymbolLine(1, 3, 4),
                new SymbolLine(1, 4, 5),
                new SymbolLine(1, 5, 6),
                new SymbolLine(2, 2, 5),
                new SymbolLine(2, 3, 6),
                new SymbolLine(2, 4, 7),
                new SymbolLine(2, 5, 8),
                new SymbolLine(3, 2, 10),
                new SymbolLine(3, 3, 20),
                new SymbolLine(3, 4, 50),
                new SymbolLine(3, 5, 100),
                new SymbolLine(4, 2, 20),
                new SymbolLine(4, 3, 50),
                new SymbolLine(4, 4, 100),
                new SymbolLine(4, 5, 200),
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

        List<SpecialSymbol> specialSymbols = new ArrayList<>();

        BoardDefinition boardDefinition = new BoardDefinition(3, 5, lines, validations, symbolLines, jokers, specialSymbols);

        Game game = new Game(NAME, PAGE, new FruitGameLogic(boardDefinition));
        return game;
    }

    @RequestMapping(value = "/" + PAGE +"/{name}")
    public String play(Model model, @PathVariable String name) {
        Session session = SessionRepository.getSession(name);
        session.setCurrentGame(create());
        Player player = session.getPlayer();
        model.addAttribute("username", name);
        model.addAttribute("credits", player.getCredits());
        return "fruitGame";
    }

}
