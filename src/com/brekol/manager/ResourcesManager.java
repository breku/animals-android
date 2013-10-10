package com.brekol.manager;

import android.graphics.Color;
import com.brekol.util.ConstantsUtil;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private BitmapTextureAtlas splashTextureAtlas, menuFontTextureAtlas, gameFontTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas, optionsTextureAtlas, aboutTextureAtlas, endGameTextureAtlas,
            recordTextureAtlas, gameTypeTextureAtlas;
    private List<BuildableBitmapTextureAtlas> gameTextureAtlasList;

    private ITextureRegion splashTextureRegion, buttonAboutTextureRegion, buttonExitTextureRegion, buttonNewGameTextureRegion,
            buttonOptionsTextureRegion, menuBackgroundTextureRegion, aboutTextureRegion, aboutBackgroundTextureRegion,
            optionsBackgroundTextureRegion, optionsTextureRegion, buttonReplayTextureRegion, endGameBackgroundTextureRegion,
            recordBackgroundTextureRegion, buttonRecordsTextureRegion, buttonClassicGameTextureRegion, buttonHalfmarathonGameTextureRegion,
            buttonMarathonGameTextureRegion, backgroundGameTypeTextureRegion;
    private Map<Integer, ITiledTextureRegion> animalTextureRegionMap;
    private Map<Integer, Sound> animalSoundMap;
    private List<Sound> winSoundList;
    private List<Sound> loseSoundList;
    private Font whiteFont, blackFont;


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
        loadGameFonts();
        loadGameMusic();
        loadEndGameResources();
    }

    public void loadEndGameResources() {
        loadEndGameGraphics();
    }

    public void loadRecordResources() {
        loadRecordGraphics();
    }

    public void loadGameTypeResources() {
        loadGameTypeGraphics();
    }

    private void loadGameTypeGraphics() {
        if (gameTypeTextureAtlas != null) {
            gameTypeTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/gametype/");
        gameTypeTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        backgroundGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "background.jpg");
        buttonClassicGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "classicButton.png");
        buttonHalfmarathonGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "halfmarathonButton.png");
        buttonMarathonGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "marathonButton.png");

        try {
            gameTypeTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            gameTypeTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }

    private void loadRecordGraphics() {
        if (recordTextureAtlas != null) {
            recordTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/record/");

        recordTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
        recordBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "recordBackground.png");

        try {
            recordTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            recordTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }


    }

    private void loadEndGameGraphics() {
        if (endGameTextureAtlas != null) {
            endGameTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/endgame/");
        endGameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);

        endGameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(endGameTextureAtlas, activity, "endGame.png");

        try {
            endGameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            endGameTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }

    private void loadGameMusic() {
        if (animalSoundMap != null) {
            return;
        }
        animalSoundMap = new HashMap<Integer, Sound>();

        SoundFactory.setAssetBasePath("mfx/animals/");

        try {
            for (int i = 0; i < ConstantsUtil.NUMBER_OF_ANIMALS * ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL; i++) {
                animalSoundMap.put(i, SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), getActivity(), i + ".ogg"));
            }
        } catch (final IOException e) {
            Debug.e(e);
        }

        SoundFactory.setAssetBasePath("mfx/other/");
        winSoundList = new ArrayList<Sound>();
        loseSoundList = new ArrayList<Sound>();
        try {
            winSoundList.add(SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), activity, "win.ogg"));
            loseSoundList.add(SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), activity, "lose.ogg"));
            loseSoundList.add(SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), activity, "lose1.ogg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadAboutGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/about/");
        aboutTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        aboutBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutTextureAtlas, activity, "background.jpg");
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

        // gameTextureAtlas has been created before, we just need to reload the textures
        if (gameTextureAtlasList != null) {
            for (BuildableBitmapTextureAtlas atlas : gameTextureAtlasList) {
                atlas.load();
            }
            return;
        }


        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlasList = new ArrayList<BuildableBitmapTextureAtlas>();
        for (int i = 0; i < ConstantsUtil.NUMBER_OF_ANIMALS + 1; i++) {
            gameTextureAtlasList.add(new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR));
        }

        buttonReplayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasList.get(gameTextureAtlasList.size() - 1), activity, "replayButton.png");


        animalTextureRegionMap = new HashMap<Integer, ITiledTextureRegion>();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/animals/");


        for (int i = 0; i < ConstantsUtil.NUMBER_OF_ANIMALS; i++) {
            animalTextureRegionMap.put(i, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasList.get(i), activity, i + ".jpg", 2, 2));
        }


        try {
            for (int i = 0; i < ConstantsUtil.NUMBER_OF_ANIMALS + 1; i++) {
                gameTextureAtlasList.get(i).build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
                gameTextureAtlasList.get(i).load();
            }
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }

    }

    public Sound[] getAnimalSound(Integer animalNumber) {
        return new Sound[]{animalSoundMap.get(animalNumber * ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL),
                animalSoundMap.get(animalNumber * ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL + 1),
                animalSoundMap.get(animalNumber * ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL + 2),
                animalSoundMap.get(animalNumber * ConstantsUtil.NUMBER_OF_SOUNDS_PER_ANIMAL + 3)};
    }

    private void loadMainMenuGraphics() {

        if (menuTextureAtlas != null) {
            menuTextureAtlas.load();
            return;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.jpg");
        buttonAboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonAbout.png");
        buttonExitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonExit.png");
        buttonNewGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonNewGame.png");
        buttonOptionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonOptions.png");
        buttonRecordsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "buttonRecords.png");

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

    private void loadGameFonts() {
        if (gameFontTextureAtlas != null) {
            gameFontTextureAtlas.load();
        }
        FontFactory.setAssetBasePath("font/");
        gameFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        blackFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), gameFontTextureAtlas, activity.getAssets(), "font1.ttf", 50, true, Color.BLACK, 2, Color.BLACK);
        gameFontTextureAtlas.load();
        blackFont.load();
    }


    private void loadMainMenuFonts() {
        if (menuFontTextureAtlas != null) {
            return;
        }
        FontFactory.setAssetBasePath("font/");
        menuFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        whiteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), menuFontTextureAtlas, activity.getAssets(), "font2.ttf", 50, true, Color.WHITE, 2, Color.WHITE);
        menuFontTextureAtlas.load();
        whiteFont.load();
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

    public void unloadEndGameTextures() {
        endGameTextureAtlas.unload();
    }

    public void unloadRecordsTextures() {
        recordTextureAtlas.unload();
    }

    public void unloadGameTypeTextures() {
        gameTypeTextureAtlas.unload();
    }

    public void unloadGameTextures() {
        for (BuildableBitmapTextureAtlas atlas : gameTextureAtlasList) {
            atlas.unload();
        }
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

    public ITextureRegion getAboutTextureRegion() {
        return aboutTextureRegion;
    }

    public ITextureRegion getAboutBackgroundTextureRegion() {
        return aboutBackgroundTextureRegion;
    }

    public ITextureRegion getOptionsBackgroundTextureRegion() {
        return optionsBackgroundTextureRegion;
    }

    public ITextureRegion getOptionsTextureRegion() {
        return optionsTextureRegion;
    }

    public Font getWhiteFont() {
        return whiteFont;
    }

    public Font getBlackFont() {
        return blackFont;
    }

    public ITiledTextureRegion getAnimalTexture(Integer animalID) {
        return animalTextureRegionMap.get(animalID);
    }

    public ITextureRegion getButtonReplayTextureRegion() {
        return buttonReplayTextureRegion;
    }

    public ITextureRegion getEndGameBackgroundTextureRegion() {
        return endGameBackgroundTextureRegion;
    }

    public ITextureRegion getRecordBackgroundTextureRegion() {
        return recordBackgroundTextureRegion;
    }

    public ITextureRegion getButtonRecordsTextureRegion() {
        return buttonRecordsTextureRegion;
    }

    public ITextureRegion getButtonClassicGameTextureRegion() {
        return buttonClassicGameTextureRegion;
    }

    public ITextureRegion getButtonHalfmarathonGameTextureRegion() {
        return buttonHalfmarathonGameTextureRegion;
    }

    public ITextureRegion getButtonMarathonGameTextureRegion() {
        return buttonMarathonGameTextureRegion;
    }

    public ITextureRegion getBackgroundGameTypeTextureRegion() {
        return backgroundGameTypeTextureRegion;
    }

    public List<Sound> getLoseSoundList() {
        return loseSoundList;
    }

    public List<Sound> getWinSoundList() {
        return winSoundList;
    }
}
