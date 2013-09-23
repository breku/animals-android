package com.brekol.manager;

import android.graphics.Color;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class ResourcesManager {

    private static final ResourcesManager INSTANCE = new ResourcesManager();
    private BaseGameActivity activity;
    private Engine engine;
    private Camera camera;
    private VertexBufferObjectManager vertexBufferObjectManager;

    private BitmapTextureAtlas splashTextureAtlas, fontTextureAtlas;
    private BuildableBitmapTextureAtlas gameTextureAtlas, menuTextureAtlas, optionsTextureAtlas, aboutTextureAtlas;

    private ITextureRegion splashTextureRegion, buttonAboutTextureRegion, buttonExitTextureRegion, buttonNewGameTextureRegion,
            buttonOptionsTextureRegion, menuBackgroundTextureRegion, waterTextureRegion, aboutTextureRegion, aboutBackgroundtTextureRegion,
            optionsBackgroundTextureRegion, optionsTextureRegion;
    private Font mediumFont;


    public static void prepareManager(Engine engine, BaseGameActivity activity, Camera camera, VertexBufferObjectManager vertexBufferObjectManager) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public void loadOptionsResources() {
        loadOptionsGraphics();
    }

    public void loadAboutResources() {
        loadAboutGraphics();
    }

    public void loadMainMenuResources() {
        loadMainMenuGraphics();
        loadMainMenuFonts();
    }

    public void loadGameResources() {
        loadGameGraphics();
    }

    private void loadAboutGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/about/");
        aboutTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        aboutBackgroundtTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutTextureAtlas, activity, "background.jpg");
        aboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutTextureAtlas, activity, "about.png");

        try {
            aboutTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            aboutTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }

    private void loadOptionsGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/options/");
        optionsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        optionsBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "background.jpg");
        optionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "options.png");

        try {
            optionsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            optionsTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }


    private void loadGameGraphics() {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        waterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "water.png");

        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            gameTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }

    }

    private void loadMainMenuGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.jpg");
        buttonAboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonAbout.png");
        buttonExitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonExit.png");
        buttonNewGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonNewGame.png");
        buttonOptionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonOptions.png");

        try {
            menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            menuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }

    public void loadSplashScreen() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
        splashTextureAtlas.load();
    }

    public void loadMenuTextures() {
        menuTextureAtlas.load();
    }


    private void loadMainMenuFonts() {
        FontFactory.setAssetBasePath("font/");
        fontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mediumFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), fontTextureAtlas, activity.getAssets(), "mediumFont2.ttf", 50, true, Color.WHITE, 2, Color.WHITE);
        mediumFont.load();
    }


    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splashTextureRegion = null;
    }


    public void unloadOptionsTextures() {
        optionsTextureAtlas.unload();
    }

    public void unloadAboutTextures() {
        aboutTextureAtlas.unload();
    }

    public void unloadGameTextures() {
        gameTextureAtlas.unload();
    }

    public void unloadMenuTextures() {
        menuTextureAtlas.unload();
    }


    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public BaseGameActivity getActivity() {
        return activity;
    }

    public Engine getEngine() {
        return engine;
    }

    public Camera getCamera() {
        return camera;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return vertexBufferObjectManager;
    }

    public ITextureRegion getSplashTextureRegion() {
        return splashTextureRegion;
    }

    public ITextureRegion getButtonAboutTextureRegion() {
        return buttonAboutTextureRegion;
    }

    public ITextureRegion getButtonExitTextureRegion() {
        return buttonExitTextureRegion;
    }

    public ITextureRegion getButtonNewGameTextureRegion() {
        return buttonNewGameTextureRegion;
    }

    public ITextureRegion getButtonOptionsTextureRegion() {
        return buttonOptionsTextureRegion;
    }

    public ITextureRegion getMenuBackgroundTextureRegion() {
        return menuBackgroundTextureRegion;
    }

    public Font getMediumFont() {
        return mediumFont;
    }

    public ITextureRegion getWaterTextureRegion() {
        return waterTextureRegion;
    }

    public ITextureRegion getAboutTextureRegion() {
        return aboutTextureRegion;
    }

    public ITextureRegion getAboutBackgroundtTextureRegion() {
        return aboutBackgroundtTextureRegion;
    }

    public ITextureRegion getOptionsBackgroundTextureRegion() {
        return optionsBackgroundTextureRegion;
    }

    public ITextureRegion getOptionsTextureRegion() {
        return optionsTextureRegion;
    }
}
