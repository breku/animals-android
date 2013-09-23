package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.model.loader.MainLevelLoader;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.LevelType;
import com.brekol.util.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class GameScene extends BaseScene {


    private HUD gameHUD;
    private Text scoreText;
    private int score;
    private SimpleLevelLoader levelLoader;
    private EntityLoader mainLevelLoader;

    @Override
    public void createScene() {
        init();
        createBackground();
        createHUD();
        loadLevel(LevelType.EASY.getID());
    }

    private void init() {
        mainLevelLoader = new MainLevelLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_LEVEL);
        levelLoader = new SimpleLevelLoader(vertexBufferObjectManager);

    }

    private void loadLevel(int levelID) {

        levelLoader.registerEntityLoader(mainLevelLoader);
        levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
    }

    private void createHUD() {
        gameHUD = new HUD();
        scoreText = new Text(130, 445, resourcesManager.getMediumFont(), "Score: 999999", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        scoreText.setText("Score: 0");

        gameHUD.attachChild(scoreText);
        camera.setHUD(gameHUD);
    }

    private void createBackground() {
        attachChild(new Sprite(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2, ResourcesManager.getInstance().getWaterTextureRegion(), vertexBufferObjectManager));
    }

    private void addToScore() {
        score++;
        scoreText.setText("Score: " + score);
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuSceneFrom(SceneType.GAME);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2);
        camera.setChaseEntity(null);
    }
}
