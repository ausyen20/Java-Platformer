package com.mygdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    Music music;
    Sound sound;
    String file;
    private static AudioManager INSTANCE = null;

    private AudioManager() {
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
    
    public void playSound() {
        sound.play();
    }

    public void disposeMusic() {
        music.dispose();
    }

    public boolean isAlreadyPlaying(String musicFile) {
        return file == musicFile;
    }
    public void setMusic(String musicFile) {
        if(music != null) music.dispose();
        music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        music.setLooping(true);  
        file = musicFile;
    }
    
   public void setSound(String soundfile) {
	   if(sound != null) sound.dispose();
       sound = Gdx.audio.newSound(Gdx.files.internal(soundfile)); 
       file = soundfile;
   }
}
