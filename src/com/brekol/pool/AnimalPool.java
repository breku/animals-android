package com.brekol.pool;

import com.brekol.model.shape.Animal;
import com.brekol.util.AnimalPosition;
import org.andengine.util.adt.pool.GenericPool;

/**
 * User: Breku
 * Date: 26.09.13
 */
public class AnimalPool extends GenericPool<Animal> {

    private static final AnimalPool INSTANCE = new AnimalPool();
    private int counter = 0;

    private AnimalPool(){
    }


    /**
     * Called when a Bullet is required but there isn't one in the pool
     */
    @Override
    protected Animal onAllocatePoolItem() {
        switch (counter%6){
            case 0:
                return new Animal(AnimalPosition.A,counter++);
            case 1:
                return new Animal(AnimalPosition.B,counter++);
            case 2:
                return new Animal(AnimalPosition.C,counter++);
            case 3:
                return new Animal(AnimalPosition.D,counter++);
            case 4:
                return new Animal(AnimalPosition.E,counter++);
            case 5:
                return new Animal(AnimalPosition.F,counter++);
            default:
                throw new UnsupportedOperationException("Wrong animal Position!");
        }
    }

    /**
     * Called when a Bullet is sent to the pool
     */
    @Override
    protected void onHandleRecycleItem(Animal pItem) {
        pItem.reset();
        pItem.setCurrentTileIndex(0);
        super.onHandleRecycleItem(pItem);
    }


    /**
     * Called just before a Bullet is returned to the caller, this is where you write your initialize code
     * i.e. set location, rotation, etc.
     */
    @Override
    protected void onHandleObtainItem(Animal pItem) {
        super.onHandleObtainItem(pItem);
    }

    public static AnimalPool getInstance() {
        return INSTANCE;
    }
}
