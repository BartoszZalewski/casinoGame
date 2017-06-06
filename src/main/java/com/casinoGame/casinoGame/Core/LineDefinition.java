package com.casinoGame.casinoGame.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LineDefinition {
    private final int id;
    private final List<Point> pointses;

    public LineDefinition(int id, List<Point> pointses) {
        this.id = id;
        this.pointses = pointses;
    }

    public int getId() {
        return id;
    }

    public List<Point> getPointses() {
        return pointses;
    }

    public List<Integer> match(int [][]board, List<Integer> jokers){
        return matchedLine(board, pointses, jokers);
    }

    public List<Integer> matchFromLeft(int [][]board, List<Integer> jokers){
        List<Integer> indexes = matchedLine(board, pointses, jokers);
        if(indexes.get(0) == 0){
            return indexes;
        }
        return new ArrayList<>();
    }

    public List<Integer> matchFromRight(int [][]board, List<Integer> jokers){
        List<Integer> indexes = matchedLine(board, pointses, jokers);
        if(indexes.get(indexes.size() - 1) == pointses.size() - 1){
            return indexes;
        }
        return new ArrayList<>();
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

    private List<Integer> matchedLine(int [][] board, List<Point> pointses, List<Integer> jokers) {
        List<Integer> indexes = new ArrayList<>();;
        List<Integer> temp = new ArrayList<>();
        int last = -1;
        int counter = 0;
        for(Point point : pointses){
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
            for(Integer integer : src) {
                dist.add(integer);
            }
        } else if(src.size() == dist.size()) {
            //TODO dwie rowne linie
            return false;
        }
        return true;
    }

    public int getFirstNoJokerSymbolId(int [][]board, List<Integer> jokers) {
        int index = 0;
        while (jokers.contains(getSymbolId(board, index))){
            index++;
            if(index >= pointses.size()) {
                return getSymbolId(board, 0);
            }
        }
        return getSymbolId(board, index);
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
