package com.brekol.pool;

import com.brekol.model.shape.Animal;
import com.brekol.util.AnimalPosition;
import com.brekol.util.ConstantsUtil;
import org.andengine.util.adt.pool.GenericPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Breku
 * Date: 26.09.13
 */
public class AnimalPool extends GenericPool<Animal> {

    private static final AnimalPool INSTANCE = new AnimalPool();
    private int counter = 0;
    private Random random = new Random();
    private List<Integer> animalIDs = new ArrayList<Integer>();
    private AnimalPosition animalPosition = AnimalPosition.F;

    private AnimalPool() {
    }


    /**
     * Called when a Bullet is required but there isn't one in the pool
     */
    @Override
    protected Animal onAllocatePoolItem() {
        switch (counter++ % 6) {
            case 0:
                return new Animal(AnimalPosition.A, getNewAnimalID());
            case 1:
                return new Animal(AnimalPosition.B, getNewAnimalID());
            case 2:
                return new Animal(AnimalPosition.C, getNewAnimalID());
            case 3:
                return new Animal(AnimalPosition.D, getNewAnimalID());
            case 4:
                return new Animal(AnimalPosition.E, getNewAnimalID());
            case 5:
                return new Animal(AnimalPosition.F, getNewAnimalID());
            default:
                throw new UnsupportedOperationException("Wrong animal Position!");
        }
    }

    private Integer getNewAnimalID() {
        Integer result = random.nextInt(ConstantsUtil.NUMBER_OF_ANIMALS);

        while (animalIDs.contains(result)) {
            result = random.nextInt(ConstantsUtil.NUMBER_OF_ANIMALS);
        }

        animalIDs.add(result);
        return result;
    }

    /**
     * Called when a Bullet is sent to the pool
     */
    @Override
    protected void onHandleRecycleItem(Animal pItem) {
        pItem.reset();
        if (getAvailableItemCount() == ConstantsUtil.NUMBER_OF_ANIMALS) {
            animalIDs.clear();
        }
        super.onHandleRecycleItem(pItem);
    }


    /**
     * Called just before a Bullet is returned to the caller, this is where you write your initialize code
     * i.e. set location, rotation, etc.
     */
    @Override
    protected void onHandleObtainItem(Animal pItem) {
        animalPosition = animalPosition.nextPosition();
        pItem.setPosition(animalPosition.getX(), animalPosition.getY());
        pItem.setCurrentTileIndex((pItem.getCurrentTileIndex() + 1) % pItem.getTileCount());
    }

    public static AnimalPool getInstance() {
        return INSTANCE;
    }
}
