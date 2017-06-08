package com.casinoGame.casinoGame.Base;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return nickName != null ? nickName.equals(player.nickName) : player.nickName == null;
    }

    @Override
    public int hashCode() {
        return nickName != null ? nickName.hashCode() : 0;
    }
}
