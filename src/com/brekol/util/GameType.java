package com.brekol.util;

/**
 * User: Breku
 * Date: 08.10.13
 */
public enum GameType {
    CLASSIC(10), HALFMARATHON(21), MARATHON(42);
    int numberOfAnimas;

    GameType(int numberOfAnimals) {
        this.numberOfAnimas = numberOfAnimals;
    }

    public int getNumberOfAnimas() {
        return numberOfAnimas;
    }
}
