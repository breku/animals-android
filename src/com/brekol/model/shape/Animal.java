package com.brekol.model.shape;

import com.brekol.manager.ResourcesManager;
import com.brekol.pool.AnimalPool;
import com.brekol.util.AnimalPosition;
import org.andengine.entity.sprite.Sprite;

/**
 * User: Breku
 * Date: 24.09.13
 */
public class Animal extends Sprite {

    // TODO add sounds

    @Override
    public void onDetached() {
        AnimalPool.getInstance().recyclePoolItem(this);
    }

    public Animal(AnimalPosition animalPosition, Integer animalNumber) {
        super(animalPosition.getX(), animalPosition.getY(),
                ResourcesManager.getInstance().getAnimalTexture(animalNumber),
                ResourcesManager.getInstance().getVertexBufferObjectManager());
    }
}
