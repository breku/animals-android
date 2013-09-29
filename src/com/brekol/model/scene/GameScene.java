package com.brekol.model.scene;

import com.brekol.loader.AnimalLoader;
import com.brekol.loader.MainLevelLoader;
import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.matcher.ClassIEntityMatcher;
import com.brekol.matcher.ClassTouchAreaMacher;
import com.brekol.model.shape.Animal;
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
    private EntityLoader animalLoader;
    private boolean isBackKeyPressed = false;

    @Override
    public void createScene() {
        init();
        createBackground();
        createHUD();
        loadLevel(LevelType.EASY.getID());
    }

    private void init() {
        clearUpdateHandlers();
        clearTouchAreas();
        mainLevelLoader = new MainLevelLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_LEVEL);
        animalLoader = new AnimalLoader<SimpleLevelEntityLoaderData>(this,ConstantsUtil.TAG_ANIMAL);
        levelLoader = new SimpleLevelLoader(vertexBufferObjectManager);

    }

    private void loadLevel(int levelID) {
        unregisterTouchAreas(new ClassTouchAreaMacher(Animal.class));
        levelLoader.registerEntityLoader(mainLevelLoader);
        levelLoader.registerEntityLoader(animalLoader);
        levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
    }

    private void createHUD() {
        gameHUD = new HUD();
        scoreText = new Text(144, 40, resourcesManager.getBlackFont(), "Score: 999999", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
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
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        if(isBackKeyPressed){
            isBackKeyPressed =false;
            Animal animal = null;
            do{
                animal = (Animal) getChildByMatcher(new ClassIEntityMatcher(Animal.class));
                detachChild(animal);
            }while (animal != null);
            detachSelf();

            SceneManager.getInstance().loadMenuSceneFrom(SceneType.GAME);
        }


    }

    @Override
    public void onBackKeyPressed() {
        isBackKeyPressed = true;
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
