package com.casinoGame.casinoGame.Line;

import com.casinoGame.casinoGame.Line.Match.MatchLine;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class Line {
    private final int id;
    private final List<Point> points;
    private final MatchLine matchLine;

    public Line(int id, List<Point> points, MatchLine matchLine) {
        this.id = id;
        this.points = points;
        this.matchLine = matchLine;
    }

    public Line(int id, List<Point> points) {
        this.id = id;
        this.points = points;
        this.matchLine = null;
    }

    public int getId() {
        return id;
    }

    public List<Integer> match(int [][]board, List<Integer> jokers){
        return matchLine.match(board, points, jokers);
    }

    public int getFirstNoJokerSymbolId(int [][]board, List<Integer> jokers) {
        int index = 0;
        while (jokers.contains(getSymbolId(board, index))){
            index++;
            if(index >= points.size()) {
                return getSymbolId(board, 0);
            }
        }
        return getSymbolId(board, index);
    }

    private int getSymbolId(int [][]board, int index) {
        Point first = points.get(index);
        int x = first.x;
        int y = first.y;
        return board[x][y];
    }

    public int size(){
        return points.size();
    }

    public List<Integer> inDefaultOrder() {
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < points.size(); i++){
            indexes.add(i);
        }
        return indexes;
    }
}
