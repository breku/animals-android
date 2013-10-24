package com.brekol.model.util;

import com.brekol.util.GameType;

/**
 * User: Breku
 * Date: 09.10.13
 */
public class HighScore {

    private Float score;
    private GameType gameType;
    private Integer coordinateX;

    public HighScore(Float score, GameType gameType) {
        this.score = score;
        this.gameType = gameType;
        switch (gameType){
            case CLASSIC:
                coordinateX = 134;
                break;
            case HALFMARATHON:
                coordinateX = 382;
                break;
            case MARATHON:
                coordinateX = 648;
                break;
            default:
                throw new UnsupportedOperationException();
        }
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

    public Integer getCoordinateX() {
        return coordinateX;
    }
}
