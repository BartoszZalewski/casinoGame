package com.casinoGame.casinoGame.Core;

import java.util.Collections;
import java.util.List;

/**
 * Created by Zalew on 2017-06-01.
 */
public class LineDefinition {
    private final int id;
    private final List<Point> pointses;

    public LineDefinition(int id, List<Point> pointses) {
        this.id = id;
        this.pointses = pointses;
    }

    public boolean match(int[][] board){
        int last = -1;
        int counter = 0;
        for(Point point : pointses){
            int id = board[point.x][point.y];
            if(id == last || last == -1){
                counter++;
            }
            last = id;
        }
        return pointses.size() == counter;
    }

    public int matchFromLeft(int [][]board, List<Integer> jokers){
        return getMatchedSymbol(board, pointses, jokers);
    }

    public int matchFromRight(int [][]board, List<Integer> jokers){
        Collections.reverse(pointses);
        int tmp = getMatchedSymbol(board, pointses, jokers);
        Collections.reverse(pointses);
        return tmp;
    }

    private int getMatchedSymbol(int [][] board, List<Point> pointses, List<Integer> jokers) {
        int last = -1;
        int counter = 0;
        for(Point point : pointses){
            int id = board[point.x][point.y];
            if(id == last || last == -1 || jokers.contains(id)){
                counter++;
            } else {
                return counter;
            }
            last = id;
        }
        return counter;
    }

    public int getFirstSymbolId(int [][]board) {
        return getSymbolId(board, 0);
    }

    public int getLastSymbolId(int [][]board) {
        return getSymbolId(board, pointses.size() - 1);
    }

    public int getCenterSymbolId(int [][]board) {
        return getSymbolId(board, pointses.size()/2);
    }

    private int getSymbolId(int [][]board, int index) {
        Point first = pointses.get(index);
        int x = first.x;
        int y = first.y;
        return board[x][y];
    }

    public int size(){
        return pointses.size();
    }

}
