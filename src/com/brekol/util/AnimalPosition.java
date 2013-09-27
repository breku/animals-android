package com.brekol.util;

/**
 * User: Breku
 * Date: 24.09.13
 */
public enum AnimalPosition {
    A(144,180),
    B(400,180),
    C(656,180),
    D(144,380),
    E(400,380),
    F(656,380);

    private int x;
    private int y;

    private AnimalPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
