package com.casinoGame.casinoGame.Line.Match;

import com.casinoGame.casinoGame.Line.Point;
import java.util.ArrayList;
import java.util.List;

public class MatchAnyLongestLine extends MatchLine {
    @Override
    public List<Integer> match(int[][] board, List<Point> points, List<Integer> jokers) {
        List<Integer> indexes = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        int last = -1;
        int counter = 0;
        for(Point point : points){
            int id = board[point.x][point.y];
            if(id == last || last == -1 || jokers.contains(id)){
                temp.add(counter);
            } else {
                checkIfBiggerList(indexes, temp);
                temp = new ArrayList<>();
                temp.add(counter);
            }
            counter++;
            if(!jokers.contains(id)) {
                last = id;
            }
        }

        checkIfBiggerList(indexes, temp);

        return indexes;
    }

    private boolean checkIfBiggerList(List<Integer> dist, List<Integer> src) {
        if(src.size() > dist.size()){
            dist.removeAll(dist);
            dist.addAll(src);
        } else if(src.size() == dist.size()) {
            //TODO dwie rowne linie
            return false;
        }
        return true;
    }
}
