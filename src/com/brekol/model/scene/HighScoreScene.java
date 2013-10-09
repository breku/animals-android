package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.model.util.HighScore;
import com.brekol.service.HighScoresService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.SceneType;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
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

    HighScoresService highScoresService;

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
    }

    private void createRecordsTable(Object... objects) {

        if (objects.length == 0) {
            createHighscoresFor(GameType.CLASSIC, 200, null);
            createHighscoresFor(GameType.HALFMARATHON, 400, null);
            createHighscoresFor(GameType.MARATHON, 600, null);
        } else {
            HighScore highScore = (HighScore) objects[0];
            createHighscoresFor(GameType.CLASSIC, 200, highScore);
            createHighscoresFor(GameType.HALFMARATHON, 400, highScore);
            createHighscoresFor(GameType.MARATHON, 600, highScore);
        }

    }

    private void createHighscoresFor(GameType gameType, int x, HighScore highScore) {

        // Top captions
        if (gameType == GameType.HALFMARATHON) {
            attachChild(new Text(x, 370, ResourcesManager.getInstance().getWhiteFont(), gameType.toString(), vertexBufferObjectManager));
        } else {
            attachChild(new Text(x, 440, ResourcesManager.getInstance().getWhiteFont(), gameType.toString(), vertexBufferObjectManager));
        }

        List<Float> highScores = highScoresService.getHighScoresFor(gameType);
        for (int i = 0; i < 3; i++) {
            if (highScore != null && highScores.get(i).equals(highScore.getScore()) && gameType == highScore.getGameType()) {

                Text text = new Text(x, 300 - i * 100, ResourcesManager.getInstance().getWhiteFont(),
                        highScores.get(i).toString(), vertexBufferObjectManager);
                text.registerEntityModifier(new ParallelEntityModifier(
                        new RotationModifier(5.0f,0.0f,360.0f),
                        new ColorModifier(5.0f, Color.WHITE,Color.GREEN),
                        new FadeInModifier(10.0f)));
                attachChild(text);
            } else {
                attachChild(new Text(x, 300 - i * 100, ResourcesManager.getInstance().getWhiteFont(),
                        highScores.get(i).toString(), vertexBufferObjectManager));
            }

        }
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
