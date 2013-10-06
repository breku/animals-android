package com.brekol.service;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.modifier.ColorBackgroundModifier;
import org.andengine.entity.scene.background.modifier.IBackgroundModifier;
import org.andengine.util.adt.color.Color;

/**
 * User: Breku
 * Date: 05.10.13
 */
public class ChangeColorService extends BaseService{

    public void changeBackgroundColorFromToAndBack(final IBackground background, final Color c1, final Color c2) {
        background.clearBackgroundModifiers();
        IBackgroundModifier colorModifier = new ColorBackgroundModifier(0.5f, c1, c2) {
            @Override
            protected void onModifierFinished(IBackground pItem) {
                super.onModifierFinished(pItem);
                pItem.registerBackgroundModifier(new ColorBackgroundModifier(0.5f, c2, c1));
            }
        };
        background.registerBackgroundModifier(colorModifier);
    }

    public void changeIEntityColorFromToAndBack(final IEntity entity, final Color c1, final Color c2) {
        entity.clearEntityModifiers();
        IEntityModifier colorModifier = new ColorModifier(0.5f, c1, c2) {
            @Override
            protected void onModifierFinished(IEntity pItem) {
                super.onModifierFinished(pItem);
                pItem.registerEntityModifier(new ColorModifier(0.5f, c2, c1));

            }
        };
        entity.registerEntityModifier(colorModifier);
    }
}
