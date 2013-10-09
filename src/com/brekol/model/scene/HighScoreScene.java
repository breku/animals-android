package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.service.HighScoresService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.SceneType;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class HighScoreScene extends BaseScene implements IOnSceneTouchListener {

    HighScoresService highScoresService;

    @Override
    public void createScene() {
        init();
        createBackground();
        createRecordsTable();

        setOnSceneTouchListener(this);
    }

    private void init() {
        highScoresService = new HighScoresService();
    }

    private void createRecordsTable() {
        createHighscoresFor(GameType.CLASSIC,200);
        createHighscoresFor(GameType.HALFMARATHON,400);
        createHighscoresFor(GameType.MARATHON,600);
    }

    private void createHighscoresFor(GameType gameType, int x){
        if(gameType == GameType.HALFMARATHON){
            attachChild(new Text(x,370,ResourcesManager.getInstance().getWhiteFont(),gameType.toString(),vertexBufferObjectManager));
        }else {
            attachChild(new Text(x,440,ResourcesManager.getInstance().getWhiteFont(),gameType.toString(),vertexBufferObjectManager));
        }

        List<Float> highScores = highScoresService.getHighScoresFor(gameType);

        for (int i = 0; i < 3; i++) {
            attachChild(new Text(x, 300 - i * 100,ResourcesManager.getInstance().getWhiteFont(),
                    highScores.get(i).toString(), vertexBufferObjectManager));
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
