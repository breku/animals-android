package com.brekol.manager;

import com.brekol.model.scene.*;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.SceneType;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();

    private SceneType currentSceneType = SceneType.SPLASH;
    private BaseScene gameScene, menuScene, loadingScene, splashScene, currentScene,
            aboutScene, optionsScene, endGameScene, recordScene, gameTypeScene;

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void setScene(BaseScene scene) {
        ResourcesManager.getInstance().getEngine().setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback onCreateSceneCallback) {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        onCreateSceneCallback.onCreateSceneFinished(splashScene);
    }


    public void createMainMenuScene() {
        ResourcesManager.getInstance().loadMainMenuResources();
        ResourcesManager.getInstance().loadGameTypeResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        setScene(menuScene);
        disposeSplashScene();
    }


    public void loadMenuSceneFrom(SceneType sceneType) {
        setScene(loadingScene);
        switch (sceneType) {
            case ABOUT:
                aboutScene.disposeScene();
                ResourcesManager.getInstance().unloadAboutTextures();
                break;
            case GAME:
                gameScene.disposeScene();
                ResourcesManager.getInstance().unloadGameTextures();
                break;
            case OPTIONS:
                ResourcesManager.getInstance().unloadOptionsTextures();
                optionsScene.disposeScene();
                break;
            case ENDGAME:
                ResourcesManager.getInstance().unloadEndGameTextures();
                endGameScene.disposeScene();
                break;
            case RECORDS:
                ResourcesManager.getInstance().unloadRecordsTextures();
                recordScene.disposeScene();
                break;
            case GAMETYPE:
                ResourcesManager.getInstance().unloadGameTypeTextures();
                gameTypeScene.disposeScene();
                break;
        }
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                ResourcesManager.getInstance().loadGameTypeResources();
                setScene(menuScene);
            }
        }));
    }

    public void loadGameTypeScene(){
        gameTypeScene = new GameTypeScene();
        setScene(gameTypeScene);
        ResourcesManager.getInstance().unloadMenuTextures();
    }

    public void loadGameScene(final GameType gameType) {
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadGameTypeTextures();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene(gameType);
                setScene(gameScene);
            }
        }));
    }

    public void loadAboutScene() {
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME / 2, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadAboutResources();
                aboutScene = new AboutScene();
                setScene(aboutScene);
            }
        }));
    }

    public void loadRecordsSceneFrom(SceneType sceneType){
        switch (sceneType){
            case MENU:
                setScene(loadingScene);
                ResourcesManager.getInstance().unloadMenuTextures();
                ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME / 2, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().loadRecordResources();
                        recordScene = new HighScoreScene();
                        setScene(recordScene);
                    }
                }));
                break;
            case GAME:
                setScene(loadingScene);
                ResourcesManager.getInstance().unloadGameTextures();
                ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME / 2, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().loadRecordResources();
                        recordScene = new HighScoreScene();
                        setScene(recordScene);
                    }
                }));
                break;
            case ENDGAME:
                ResourcesManager.getInstance().loadRecordResources();
                recordScene = new HighScoreScene();
                setScene(recordScene);
            default:
                throw new UnsupportedOperationException();
        }

    }

    public void loadEndGameScene() {
        endGameScene = new EndGameScene();
        setScene(endGameScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
    }

    public void loadOptionsScene() {
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ConstantsUtil.LOADING_SCENE_TIME / 2, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadOptionsResources();
                optionsScene = new OptionsScene();
                setScene(optionsScene);
            }
        }));
    }


    private void disposeSplashScene() {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }


    public BaseScene getCurrentScene() {
        return currentScene;
    }

}
