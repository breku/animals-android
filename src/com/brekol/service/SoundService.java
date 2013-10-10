package com.brekol.service;

import org.andengine.audio.sound.Sound;

import java.util.List;
import java.util.Random;

/**
 * User: Breku
 * Date: 10.10.13
 */
public class SoundService extends BaseService {

    private List<Sound> winSoundList;
    private List<Sound> loseSoundList;
    private Random random;


    public SoundService() {
        init();
    }

    private void init() {
        winSoundList = resourcesManager.getWinSoundList();
        loseSoundList = resourcesManager.getLoseSoundList();
        random = new Random();
    }


    public void playWinSound() {
        winSoundList.get(random.nextInt(winSoundList.size())).play();
    }

    public void playLoseSound() {
        loseSoundList.get(random.nextInt(loseSoundList.size())).play();
    }
}
