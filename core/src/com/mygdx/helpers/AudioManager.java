package com.mygdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {

    Music music;
    private static AudioManager INSTANCE = null;

    public AudioManager() {
        INSTANCE = this;
    }

    public static Object getInstance() {
        if(INSTANCE == null)
        INSTANCE = new AudioManager();
    
        return INSTANCE;
    }

    public void playMusic() {
        music.play();
    }

    public void setMusic(String musicFile) {
        music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        music.setLooping(true);        
    }
    
}
