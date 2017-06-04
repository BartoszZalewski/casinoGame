package com.casinoGame.casinoGame.Core;


public class Player {

    public final String nickName;
    private int credits;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public Player(String nickName, int credits) {
        this.nickName = nickName;
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int value) {
        credits += value;
    }

    public void deleteCredits(int value) {
        credits -= value;
    }

    @Override
    public String toString() {
        return "{Nick: " + nickName + ", credits: " + credits + "}";
    }
}
