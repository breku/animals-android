package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.model.util.HighScore;
import com.brekol.service.HighScoresService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.SceneType;
import org.andengine.entity.modifier.*;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class HighScoreScene extends BaseScene implements IOnSceneTouchListener {

    private HighScoresService highScoresService;
    private boolean greenHighscoreItemCreated;

    /**
     * Constructor
     *
     * @param objects object[0] - HighScore
     */
    public HighScoreScene(Object... objects) {
        super(objects);
    }

    @Override
    public void createScene(Object... objects) {
        init();
        createBackground();
        createRecordsTable(objects);

        setOnSceneTouchListener(this);
    }

    private void init() {
        highScoresService = new HighScoresService();
        greenHighscoreItemCreated = false;
    }

    private void createRecordsTable(Object... objects) {

        if (objects.length == 0) {
            createHighscoresFor(GameType.CLASSIC, 134, null);
            createHighscoresFor(GameType.HALFMARATHON, 382, null);
            createHighscoresFor(GameType.MARATHON, 648, null);
        } else {
            HighScore highScore = (HighScore) objects[0];
            createHighscoresFor(GameType.CLASSIC, 134, highScore);
            createHighscoresFor(GameType.HALFMARATHON, 382, highScore);
            createHighscoresFor(GameType.MARATHON, 648, highScore);
        }

    }

    private void createHighscoresFor(GameType gameType, int x, HighScore highScore) {

        List<Float> highScores = highScoresService.getHighScoresFor(gameType);
        for (int i = 0; i < 3; i++) {
            if (highScore != null && highScores.get(i).equals(highScore.getScore()) && gameType == highScore.getGameType()) {
                createGreenHighscoreItem(x, i, highScores.get(i));
                greenHighscoreItemCreated = true;
            } else {
                createHighscoreItem(x, i, highScores.get(i));
            }
        }

        if(!greenHighscoreItemCreated && highScore!= null){
            createRedHighscoreItem(highScore);
        }
    }

    private void createRedHighscoreItem(HighScore highScore){
        Text text = new Text(highScore.getCoordinateX(), 40, ResourcesManager.getInstance().getGreenFont(),
                highScore.getScore().toString(), vertexBufferObjectManager);
        text.registerEntityModifier(new ParallelEntityModifier(
                new ColorModifier(1.0f,Color.WHITE,Color.RED),
                new RotationModifier(4.0f,-180.0f,0.0f),
                new FadeOutModifier(10.0f)));
        attachChild(text);
    }


    private void createGreenHighscoreItem(Integer x, Integer i, Float highScore) {
        Text text = new Text(x, 280 - i * 40, ResourcesManager.getInstance().getGreenFont(),
                highScore.toString(), vertexBufferObjectManager);
        text.registerEntityModifier(new ParallelEntityModifier(
                new RotationModifier(5.0f, 0.0f, 360.0f),
                new ColorModifier(5.0f, Color.WHITE, Color.GREEN),
                new FadeInModifier(10.0f)));
        attachChild(text);

    }

    private void createHighscoreItem(Integer x, Integer i, Float highScore) {
        attachChild(new Text(x, 280 - i * 40, ResourcesManager.getInstance().getGreenFont(),
                highScore.toString(), vertexBufferObjectManager));

    }

    private void createTopCaption(GameType gameType, Integer x, Integer y) {
        attachChild(new Text(x, y, ResourcesManager.getInstance().getGreenFont(), gameType.toString(), vertexBufferObjectManager));
    }

    private void createBackground() {
        attachChild(new Sprite(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2,
                ResourcesManager.getInstance().getRecordBackgroundTextureRegion(), vertexBufferObjectManager));

    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuSceneFrom(SceneType.RECORDS);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.RECORDS;
    }

    @Override
    public void disposeScene() {
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionUp()) {
            SceneManager.getInstance().loadMenuSceneFrom(SceneType.RECORDS);
        }
        return false;
    }
}
