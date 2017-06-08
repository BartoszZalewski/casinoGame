package com.casinoGame.casinoGame.Line.Match;

import com.casinoGame.casinoGame.Line.Point;
import java.util.List;

public abstract class MatchLine {

    public abstract List<Integer> match(int [][] board, List<Point> points, List<Integer> jokers);

}
