package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;
import com.mygdx.helpers.AudioManager;

public class LevelLust extends GameScreen {
	    
    public LevelLust() {
        constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerLeftOffset = (camera.position.x * 100) - player.getX();
        playerSpeed = player.getSpeed();
        
        // Set background texture
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");

        // Set music
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/spy-jazz-20925.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }

    public Player getPlayer() {
    	return player;
    }

    @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.LUST;
    }

    @Override
    public LevelScreenTypes getCurrentScreen() {
        return LevelScreenTypes.LUST;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.GLUTTONY;
    }
    
}