package com.brekol.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.brekol.util.GameType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Breku
 * Date: 07.10.13
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myDB";
    private static final String TABLE_NAME = "HIGH_SCORES";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_GAME_TYPE = "GAME_TYPE";
    private static final String COLUMN_SCORE = "SCORE";
    private static final int DATABASE_VERSION = 20;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when database does not exists
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GAME_TYPE + " TEXT, " +
                COLUMN_SCORE + " REAL" +
                ")");
        createDefaultHighScoreValues(sqLiteDatabase);
    }


    /**
     * Is called when DATABASE_VERSION is upgraded
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Float> getHighScoresFor(GameType gameType) {
        List<Float> result = new ArrayList<Float>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + COLUMN_SCORE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_GAME_TYPE + " = ?", new String[]{gameType.toString()});
        while (cursor.moveToNext()) {
            result.add(cursor.getFloat(0));
        }
        Collections.sort(result);
        cursor.close();
        database.close();
        return result;
    }

    public void addToHighScores(GameType gameType, float score) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GAME_TYPE, gameType.toString());
        values.put(COLUMN_SCORE, score);
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void removeLastResult(GameType gameType) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM HIGH_SCORES WHERE ID = (SELECT ID FROM HIGH_SCORES WHERE GAME_TYPE = ? ORDER BY SCORE DESC LIMIT 1)", new String[]{gameType.toString()});
        database.close();
    }


    public boolean isRecord(float score, GameType gameType) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + COLUMN_SCORE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_GAME_TYPE + " = ?", new String[]{gameType.toString()});
        while (cursor.moveToNext()) {
            if (score < cursor.getLong(0)) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        database.close();
        return false;
    }

    private boolean isTableExists(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    private void createDefaultHighScoreValues(SQLiteDatabase sqLiteDatabase) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TYPE, GameType.CLASSIC.toString());
        contentValues.put(COLUMN_SCORE, 999.0f);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        contentValues.put(COLUMN_GAME_TYPE, GameType.HALFMARATHON.toString());
        contentValues.put(COLUMN_SCORE, 999.0f);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        contentValues.put(COLUMN_GAME_TYPE, GameType.MARATHON.toString());
        contentValues.put(COLUMN_SCORE, 999.0f);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

}
