package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.GameType;
import com.brekol.util.SceneType;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

/**
 * User: Breku
 * Date: 08.10.13
 */
public class GameTypeScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {

    private MenuScene menuScene;
    private final int CLASSIC_GAME = 0;
    private final int HALFMARATHON_GAME = 1;
    private final int MARATHON_GAME = 2;


    @Override
    public void createScene(Object... objects) {
        createBackground();
        createButtons();
    }

    private void createBackground() {
        attachChild(new Sprite(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2, resourcesManager.getBackgroundGameTypeTextureRegion(), vertexBufferObjectManager));
    }

    private void createButtons() {
        menuScene = new MenuScene(camera);
        menuScene.setPosition(0, 0);

        final IMenuItem classicGameItem = new ScaleMenuItemDecorator(new SpriteMenuItem(CLASSIC_GAME, ResourcesManager.getInstance().getButtonClassicGameTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        final IMenuItem halfmarathonGameItem = new ScaleMenuItemDecorator(new SpriteMenuItem(HALFMARATHON_GAME, ResourcesManager.getInstance().getButtonHalfmarathonGameTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        final IMenuItem marathonGameItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MARATHON_GAME, ResourcesManager.getInstance().getButtonMarathonGameTextureRegion(), vertexBufferObjectManager), 1.2f, 1);

        menuScene.addMenuItem(classicGameItem);
        menuScene.addMenuItem(halfmarathonGameItem);
        menuScene.addMenuItem(marathonGameItem);

        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        classicGameItem.setPosition(400, 317);
        halfmarathonGameItem.setPosition(400, 207);
        marathonGameItem.setPosition(400, 97);

        menuScene.setOnMenuItemClickListener(this);

        setChildScene(menuScene);

    }


    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuSceneFrom(SceneType.GAMETYPE);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAMETYPE;
    }

    @Override
    public void disposeScene() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case CLASSIC_GAME:
                SceneManager.getInstance().loadGameScene(GameType.CLASSIC);
                break;
            case HALFMARATHON_GAME:
                SceneManager.getInstance().loadGameScene(GameType.HALFMARATHON);
                break;
            case MARATHON_GAME:
                SceneManager.getInstance().loadGameScene(GameType.MARATHON);
                break;
            default:
                return false;
        }
        return false;
    }
}
