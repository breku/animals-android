package com.brekol.service;

import com.brekol.util.GameType;

import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class HighScoresService extends BaseService {

    DatabaseHelper databaseHelper = new DatabaseHelper(activity);

    public List<Float> getHighScoresFor(GameType gameType) {
        return databaseHelper.getHighScoresFor(gameType);
    }

    public boolean isRecord(float score, GameType gameType) {
        return databaseHelper.isRecord(score, gameType);
    }

    public void addNewRecord(float score, GameType gameType) {
        databaseHelper.removeLastResult(gameType);
        databaseHelper.addToHighScores(gameType, score);
    }

}
