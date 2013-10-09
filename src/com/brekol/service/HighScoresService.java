package com.brekol.service;

import com.brekol.model.util.HighScore;
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

    public boolean isRecord(HighScore highScore) {
        return databaseHelper.isRecord(highScore.getScore(), highScore.getGameType());
    }

    public void addNewRecord(HighScore highScore) {
        databaseHelper.removeLastResult(highScore.getGameType());
        databaseHelper.addToHighScores(highScore.getGameType(), highScore.getScore());
    }

}
