package com.brekol.model.util;

import com.brekol.util.GameType;

/**
 * User: Breku
 * Date: 09.10.13
 */
public class HighScore {

    private Float score;
    private GameType gameType;

    public HighScore(Float score, GameType gameType) {
        this.score = score;
        this.gameType = gameType;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
