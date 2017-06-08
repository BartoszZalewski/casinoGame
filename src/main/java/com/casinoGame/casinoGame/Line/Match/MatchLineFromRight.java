package com.casinoGame.casinoGame.Line.Match;

import com.casinoGame.casinoGame.Line.Point;
import java.util.ArrayList;
import java.util.List;

public class MatchLineFromRight extends MatchLine {
    @Override
    public List<Integer> match(int[][] board, List<Point> points, List<Integer> jokers) {
        List<Integer> indexes = new ArrayList<>();
        int last = -1;
        for(int index = points.size() - 1; index >= 0; index--){
            Point point = points.get(index);
            int id = board[point.x][point.y];
            if(id == last || last == -1 || jokers.contains(id)){
                indexes.add(index);
            } else {
                return indexes;
            }
            if(!jokers.contains(id)) {
                last = id;
            }
        }
        return indexes;
    }
}
