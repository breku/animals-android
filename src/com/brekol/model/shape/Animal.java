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
    private boolean isSoundPlayed = false;
    private Random random = new Random();
    private Integer soundID;


    public Animal(AnimalPosition animalPosition, Integer animalID) {
        super(animalPosition.getX(), animalPosition.getY(),
                ResourcesManager.getInstance().getAnimalTexture(animalID),
                ResourcesManager.getInstance().getVertexBufferObjectManager());
        animalSound = ResourcesManager.getInstance().getAnimalSound(animalID);
        soundID = random.nextInt(ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL);
        setIgnoreUpdate(true);
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
                    return true;
            }

        }
        return false;
    }

    public void stopMusic() {
        animalSound[soundID].stop();
    }

    public void playMusic() {
        isSoundPlayed = true;
        animalSound[soundID].play();
    }

    @Override
    public void reset() {
        super.reset();
        isClicked = false;
        isSoundPlayed = false;
        soundID = random.nextInt(ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL);
    }

    public boolean isSoundPlayed() {
        return isSoundPlayed;
    }

    public boolean isClicked() {
        return isClicked;
    }
}
