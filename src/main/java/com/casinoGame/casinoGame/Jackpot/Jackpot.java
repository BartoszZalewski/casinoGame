package com.casinoGame.casinoGame.Jackpot;


import com.casinoGame.casinoGame.Base.Event;
import java.util.Random;

public class Jackpot {
    private final String name;
    private final int rangeFrom;
    private final int rangeTo;
    private final double betPercent;
    private int hitValue;
    private double currentValue;

    public Jackpot(String name, int rangeFrom, int rangeTo, double betPercent){
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.betPercent = betPercent;
        hitValue = new Random().nextInt(rangeTo - rangeFrom) + rangeFrom;
    }

    public void increase(double value){
        currentValue += value * betPercent;
    }

    public void reset(){
        currentValue = 0;
        hitValue = new Random().nextInt(rangeTo - rangeFrom) + rangeFrom;
    }

    public boolean check(){
        if(hitValue < currentValue) {
            return true;
        }
        return false;
    }

    public int getValue(){
        return (int) Math.floor(currentValue);
    }

    public Event getEvent() {
        return new Event("Jackpot", getValue(), null, null, name);
    }
}
