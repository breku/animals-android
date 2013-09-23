package com.brekol.util;

/**
 * User: Breku
 * Date: 21.09.13
 */
public enum LevelType {
    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXTREME(4);

    private int ID;

    LevelType(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
