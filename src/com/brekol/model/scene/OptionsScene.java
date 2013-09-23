package com.brekol.model.scene;

import com.brekol.manager.SceneManager;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.SceneType;
import org.andengine.entity.sprite.Sprite;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class OptionsScene extends BaseScene {
    @Override
    public void createScene() {
        createBackground();
    }

    private void createBackground() {
        attachChild(new Sprite(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2, resourcesManager.getOptionsBackgroundTextureRegion(), vertexBufferObjectManager));
        attachChild(new Sprite(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2, resourcesManager.getOptionsTextureRegion(), vertexBufferObjectManager));
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuSceneFrom(SceneType.OPTIONS);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.OPTIONS;
    }

    @Override
    public void disposeScene() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
