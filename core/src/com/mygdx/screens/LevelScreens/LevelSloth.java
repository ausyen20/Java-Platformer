package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;

public class LevelSloth extends GameScreen {

    public LevelSloth() {
        constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerLeftOffset = (camera.position.x * 100) - player.getX();
        playerSpeed = player.getSpeed();
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/sloth00.png");
        backgrounds[1] = new Texture("backgrounds/sloth01.png");
        backgrounds[2] = new Texture("backgrounds/sloth02.png");
        
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/Slothmusic.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }
    
    public Player getPlayer() {
    	return player;
    }

    @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.GLUTTONY;
    }

    @Override
    public LevelScreenTypes getCurrentScreen() {
    	return LevelScreenTypes.SLOTH;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.GREED;
    }
}