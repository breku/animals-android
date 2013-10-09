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
import com.brekol.service.ChangeColorService;
import com.brekol.service.HighScoresService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.LevelType;
import com.brekol.util.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
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
    private Text timeText;
    private Integer numberOfGuessedAnimals;
    private SimpleLevelLoader levelLoader;
    private EntityLoader mainLevelLoader;
    private EntityLoader animalLoader;
    private Random random;
    private boolean isBackKeyPressed = false;
    private Integer animalID;
    private List<Animal> animalList;
    private AnimalService animalService;
    private HighScoresService highScoresService;
    private ChangeColorService changeColorService;
    private int firstRunCounter = 0;
    private IEntity bottomWhiteRectangle;
    private GameType currentGameType;
    private long startTime;

    public GameScene(GameType gameType) {
        super();
        currentGameType = gameType;
    }


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
        if (AnimalPool.getInstance().getAvailableItemCount() != ConstantsUtil.NUMBER_OF_ANIMALS) {
            AnimalPool.getInstance().batchAllocatePoolItems(ConstantsUtil.NUMBER_OF_ANIMALS);
        }
        changeColorService = new ChangeColorService();
        animalService = new AnimalService();
        highScoresService = new HighScoresService();

        mainLevelLoader = new MainLevelLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_LEVEL);
        animalLoader = new AnimalLoader<SimpleLevelEntityLoaderData>(this, ConstantsUtil.TAG_ANIMAL);
        levelLoader = new SimpleLevelLoader(vertexBufferObjectManager);

        levelLoader.registerEntityLoader(mainLevelLoader);
        levelLoader.registerEntityLoader(animalLoader);

        random = new Random();
        numberOfGuessedAnimals = 0;
        firstRunCounter = 0;
    }

    private void loadLevel(int levelID) {
        unregisterTouchAreas(new ClassTouchAreaMacher(Animal.class));
        detachAnimals();
        AnimalPool.getInstance().shufflePoolItems();
        animalID = random.nextInt(ConstantsUtil.NUMBER_OF_ANIMALS_ON_THE_GAME_SCENE);
        levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
        animalList = animalService.getCurrentAnimals(mChildren);
        if (firstRunCounter > 0) {
            animalService.playAnimalSound(animalList, animalID);
        }
    }

    private void createHUD() {
        gameHUD = new HUD();

        timeText = new Text(150, 40, resourcesManager.getBlackFont(), "Time: 999999999999", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);

        gameHUD.attachChild(timeText);
        camera.setHUD(gameHUD);
    }

    private void createBackground() {
        unregisterTouchAreas(new ClassTouchAreaMacher(Sprite.class));
        setBackground(new Background(Color.WHITE));
        bottomWhiteRectangle = new Rectangle(ConstantsUtil.SCREEN_WIDTH / 2, 40, ConstantsUtil.SCREEN_WIDTH, 80, vertexBufferObjectManager);
        bottomWhiteRectangle.setColor(Color.WHITE);
        attachChild(bottomWhiteRectangle);
        createReplayButton();
    }

    private void createReplayButton() {
        Sprite replayButton = new Sprite(700, 40, ResourcesManager.getInstance().getButtonReplayTextureRegion(), vertexBufferObjectManager) {
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

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        if (isBackKeyPressed) {
            loadMainMenuScene();
        }

        if (firstRunCounter++ == 1) {
            animalService.playAnimalSound(animalList, animalID);
            startTime = System.currentTimeMillis();
        }

        updateTime();

        Animal clickedAnimal = animalService.getClickedAnimal(animalList);
        if (clickedAnimal != null) {
            if (clickedAnimal == animalService.getPlayedAnimal(animalList)) {
                // OK - next level
                animalService.good();
                animalService.stopSound(animalList);
                numberOfGuessedAnimals++;

                if(numberOfGuessedAnimals == currentGameType.getNumberOfAnimas()){
                    float score = (System.currentTimeMillis() - startTime) / 1000.0f;
                    if(highScoresService.isRecord(score,currentGameType)){
                        highScoresService.addNewRecord(score,currentGameType);
                    }
                    detachAnimals();
                    SceneManager.getInstance().loadRecordsSceneFrom(SceneType.GAME);

                }else {
                    changeColorService.changeIEntityColorFromToAndBack(bottomWhiteRectangle, Color.WHITE, Color.GREEN);
                    loadLevel(LevelType.EASY.getID());
                }


            } else {
                // FAIL
                animalService.fail();
                detachAnimals();
                SceneManager.getInstance().loadEndGameScene();
            }
        }
    }

    private void updateTime() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 10;
        timeText.setText("Time:   " + elapsedTime / 100 + "." + elapsedTime % 100);
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
