package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.SceneType;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class LoadingScene extends BaseScene {

    @Override
    public void createScene() {
        setBackground(new Background(Color.BLACK));
        attachChild(new Text(ConstantsUtil.SCREEN_WIDTH / 2, ConstantsUtil.SCREEN_HEIGHT / 2, ResourcesManager.getInstance().getMediumFont(),
                "Loading...", vertexBufferObjectManager));
    }

    @Override
    public void onBackKeyPressed() {
        return;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.LOADING;
    }

    @Override
    public void disposeScene() {
    }
}
