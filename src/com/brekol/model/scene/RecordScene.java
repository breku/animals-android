package com.brekol.model.scene;

import com.brekol.manager.ResourcesManager;
import com.brekol.manager.SceneManager;
import com.brekol.service.RecordsService;
import com.brekol.util.ConstantsUtil;
import com.brekol.util.SceneType;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class RecordScene extends BaseScene implements IOnSceneTouchListener {

    List<Integer> recordList;
    RecordsService recordsService;

    @Override
    public void createScene() {
        init();
        createBackground();
        createRecordsTable();

        setOnSceneTouchListener(this);
    }

    private void init() {
        recordList = new ArrayList<Integer>();
        recordsService = new RecordsService();
    }

    private void createRecordsTable() {
        recordList = recordsService.getSortedRecordsList();

        for (int i = 0; i < 3; i++) {
            attachChild(new Text(ConstantsUtil.SCREEN_WIDTH / 2, 300 - i * 100,
                    ResourcesManager.getInstance().getWhiteFont(), recordList.get(i).toString(), vertexBufferObjectManager));
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
