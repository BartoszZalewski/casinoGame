package com.casinoGame.casinoGame.Game;

import com.casinoGame.casinoGame.Base.*;
import com.casinoGame.casinoGame.Game.Logic.Logic;
import com.casinoGame.casinoGame.Game.Logic.SpaceGameLogic;
import com.casinoGame.casinoGame.Line.Line;
import com.casinoGame.casinoGame.Line.Match.MatchAnyLongestLine;
import com.casinoGame.casinoGame.Line.Point;
import com.casinoGame.casinoGame.Session;
import com.casinoGame.casinoGame.SessionRepository;
import com.casinoGame.casinoGame.SpecialSymbol.SpecialSymbol;
import com.casinoGame.casinoGame.SpecialSymbol.TreasureSymbol;
import com.casinoGame.casinoGame.Validations.Validation;
import com.casinoGame.casinoGame.Validations.VerticalLineValidation;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
public class SpaceGame extends Game{

    public final String NAME = "SpaceGame";
    public final String PAGE = "defaultGame";

    public SpaceGame(){}

    public SpaceGame(Logic logic) {
        super(logic);
    }

    public Game create() {
        List<Line> lines = Arrays.asList(
                new Line(0, Arrays.asList(
                        new Point(0,0),
                        new Point(1,0),
                        new Point(2,0),
                        new Point(3,0),
                        new Point(4,0)),
                        new MatchAnyLongestLine()
                ),
                new Line(1, Arrays.asList(
                        new Point(0,1),
                        new Point(1,1),
                        new Point(2,1),
                        new Point(3,1),
                        new Point(4,1)),
                        new MatchAnyLongestLine()
                ),
                new Line(2, Arrays.asList(
                        new Point(0,2),
                        new Point(1,2),
                        new Point(2,2),
                        new Point(3,2),
                        new Point(4,2)),
                        new MatchAnyLongestLine()
                ),
                new Line(3, Arrays.asList(
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,1),
                        new Point(4,0)),
                        new MatchAnyLongestLine()
                ),
                new Line(4, Arrays.asList(
                        new Point(0,2),
                        new Point(1,1),
                        new Point(2,0),
                        new Point(3,1),
                        new Point(4,2)),
                        new MatchAnyLongestLine()
                )
        );

        List<SymbolLine> symbolLines = Arrays.asList(
                new SymbolLine(0, 3, 2),
                new SymbolLine(0, 4, 3),
                new SymbolLine(0, 5, 4),
                new SymbolLine(1, 3, 4),
                new SymbolLine(1, 4, 5),
                new SymbolLine(1, 5, 6),
                new SymbolLine(2, 3, 6),
                new SymbolLine(2, 4, 7),
                new SymbolLine(2, 5, 8),
                new SymbolLine(3, 3, 20),
                new SymbolLine(3, 4, 50),
                new SymbolLine(3, 5, 100),
                new SymbolLine(4, 3, 50),
                new SymbolLine(4, 4, 100),
                new SymbolLine(4, 5, 200),
                new SymbolLine(10, 3, 50),
                new SymbolLine(10, 4, 100),
                new SymbolLine(10, 5, 200),
                new SymbolLine(11, 3, 100),
                new SymbolLine(11, 4, 200),
                new SymbolLine(11, 5, 300),
                new SymbolLine(12, 3, 100),
                new SymbolLine(12, 4, 200),
                new SymbolLine(12, 5, 300),
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
                                put(6,1);
                                put(10,2);
                                put(11,2);
                                put(12,1);
                                put(13,1);
                                put(14,1);
                            }
                        }
                )
        );

        List<Integer> bets = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500);

        List<SpecialSymbol> specialSymbols = Arrays.asList(
                new TreasureSymbol(14, "TreasureSymbol1",3, new Range(3, 300)),
                new TreasureSymbol(6, "TreasureSymbol2",2, new Range(2, 100))

                );

        BoardDefinition boardDefinition = new BoardDefinition.Builder(3, 5)
                .withLines(lines)
                .withJokers(jokers)
                .withSymbols(symbolLines)
                .withSpecialSymbols(specialSymbols)
                .withValidations(validations)
                .withBets(bets)
                .build();

        return new SpaceGame(new SpaceGameLogic(boardDefinition));
    }

    @RequestMapping(value = "/" + NAME +"/{name}")
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
