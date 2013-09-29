package com.brekol.loader;

import com.brekol.model.scene.BaseScene;
import com.brekol.model.shape.Animal;
import com.brekol.pool.AnimalPool;
import org.andengine.entity.IEntity;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.IEntityLoaderData;
import org.xml.sax.Attributes;

import java.io.IOException;

/**
 * User: Breku
 * Date: 24.09.13
 */
public class AnimalLoader<T extends IEntityLoaderData> extends EntityLoader<T>{

    private BaseScene scene;

    public AnimalLoader(BaseScene scene,String... pEntityNames) {
        super(pEntityNames);
        this.scene = scene;
    }

    @Override
    public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, IEntityLoaderData pEntityLoaderData) throws IOException {
        final Animal levelObject = AnimalPool.getInstance().obtainPoolItem();
        scene.registerTouchArea(levelObject);
        return levelObject;
    }
}
