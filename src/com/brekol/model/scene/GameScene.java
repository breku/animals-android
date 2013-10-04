package com.brekol.model.scene;

import com.brekol.loader.AnimalLoader;
import com.brekol.loader.MainLevelLoader;
import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.matcher.ClassIEntityMatcher;
import com.brekol.matcher.ClassTouchAreaMacher;
import com.brekol.model.shape.Animal;
import com.brekol.pool.AnimalPool;
import com.brekol.service.AnimalService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.LevelType;
import com.brekol.util.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;

import java.util.List;
import java.util.Random;

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
    private Random random;
    private boolean isBackKeyPressed = false;
    private Integer animalID;
    private List<Animal> animalList;
    private AnimalService animalService;

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
        AnimalPool.getInstance().batchAllocatePoolItems(ConstantsUtil.NUMBER_OF_ANIMALS);
        animalService = new AnimalService();
        mainLevelLoader = new MainLevelLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_LEVEL);
        animalLoader = new AnimalLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_ANIMAL);
        levelLoader = new SimpleLevelLoader(vertexBufferObjectManager);

        levelLoader.registerEntityLoader(mainLevelLoader);
        levelLoader.registerEntityLoader(animalLoader);

        random = new Random();
    }

    private void loadLevel(int levelID) {
        unregisterTouchAreas(new ClassTouchAreaMacher(Animal.class));
        detachAnimals();
        AnimalPool.getInstance().shufflePoolItems();
        animalID = random.nextInt(ConstantsUtil.NUMBER_OF_ANIMALS_ON_THE_GAME_SCENE);
        levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
        animalList = animalService.getCurrentAnimals(mChildren);
        animalService.playAnimalSound(animalList, animalID);
    }

    private void createHUD() {
        gameHUD = new HUD();
        scoreText = new Text(144, 40, resourcesManager.getBlackFont(), "Score: 999999", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        scoreText.setText("Score: 0");

        gameHUD.attachChild(scoreText);
        camera.setHUD(gameHUD);
    }

    private void createBackground() {
        unregisterTouchAreas(new ClassTouchAreaMacher(Sprite.class));
        setBackground(new Background(Color.WHITE));
        createReplayButton();
    }

    private void createReplayButton() {
        Sprite replayButton = new Sprite(480, 40, ResourcesManager.getInstance().getButtonReplayTextureRegion(), vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP:
                        animalService.playAnimalSound(animalList, animalID);
                        return true;
                }
                return false;
            }
        };
        attachChild(replayButton);
        registerTouchArea(replayButton);

    }

    private void addToScore() {
        score++;
        scoreText.setText("Score: " + score);
    }


    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (isBackKeyPressed) {
            loadMainMenuScene();
        }
        Animal clickedAnimal = animalService.getClickedAnimal(animalList);
        if (clickedAnimal != null) {
            if (clickedAnimal == animalService.getPlayedAnimal(animalList)) {
                animalService.good();
                loadLevel(LevelType.EASY.getID());
            } else {
                animalService.fail();
                loadLevel(LevelType.EASY.getID());
//                SceneManager.getInstance().loadEndGameScene();
            }
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

    private void loadMainMenuScene() {
        isBackKeyPressed = false;
        detachAnimals();
        detachSelf();
        SceneManager.getInstance().loadMenuSceneFrom(SceneType.GAME);

    }

    private void detachAnimals() {
        Animal animal;
        do {
            animal = (Animal) getChildByMatcher(new ClassIEntityMatcher(Animal.class));
            detachChild(animal);
        } while (animal != null);
    }


}
