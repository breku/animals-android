package com.brekol.model.shape;

import com.brekol.manager.ResourcesManager;
import com.brekol.pool.AnimalPool;
import com.brekol.util.AnimalPosition;
import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * User: Breku
 * Date: 24.09.13
 */
public class Animal extends Sprite {

    // TODO add sounds

    private Sound animalSound;
    private boolean isClicked = false;


    public Animal(AnimalPosition animalPosition, Integer animalNumber) {
        super(animalPosition.getX(), animalPosition.getY(),
                ResourcesManager.getInstance().getAnimalTexture(animalNumber),
                ResourcesManager.getInstance().getVertexBufferObjectManager());
        animalSound = ResourcesManager.getInstance().getAnimalSound(animalNumber);
    }

    @Override
    public void onDetached() {
        AnimalPool.getInstance().recyclePoolItem(this);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if (!isClicked){
            switch (pSceneTouchEvent.getAction()){
                case TouchEvent.ACTION_UP:
                    isClicked = true;
                    animalSound.play();
                    isClicked = false;
                    return true;
            }

        }
        return false;

    }
}
