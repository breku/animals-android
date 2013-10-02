package com.brekol.model.shape;

import com.brekol.manager.ResourcesManager;
import com.brekol.pool.AnimalPool;
import com.brekol.util.AnimalPosition;
import com.brekol.util.ConstantsUtil;
import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

import java.util.Random;

/**
 * User: Breku
 * Date: 24.09.13
 */
public class Animal extends AnimatedSprite {

    private Sound[] animalSound = new Sound[ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL];
    private boolean isClicked = false;
    private Random random = new Random();


    public Animal(AnimalPosition animalPosition, Integer animalID) {
        super(animalPosition.getX(), animalPosition.getY(),
                ResourcesManager.getInstance().getAnimalTexture(animalID),
                ResourcesManager.getInstance().getVertexBufferObjectManager());
        animalSound = ResourcesManager.getInstance().getAnimalSound(animalID);
    }

    @Override
    public void onDetached() {
        AnimalPool.getInstance().recyclePoolItem(this);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if (!isClicked) {
            switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_UP:
                    isClicked = true;
                    animalSound[random.nextInt(ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL)].play();

                    this.setCurrentTileIndex((this.getCurrentTileIndex() + 1) % this.getTileCount());
                    isClicked = false;
                    return true;
            }

        }
        return false;

    }
}
