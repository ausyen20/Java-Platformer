package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;

public class LevelEnvy extends GameScreen {
	

    public LevelEnvy() {
    	 constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
         cameraScrollingSpeed = constantScrollingSpeed;
         cameraUpdate();
         playerLeftOffset = (camera.position.x * 100) - player.getX();
         playerSpeed = player.getSpeed();
         mintLinearVel = new Vector2(0,0);
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/envy00.png");
        backgrounds[1] = new Texture("backgrounds/envy01.png");
        backgrounds[2] = new Texture("backgrounds/envy02.png");
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/Envy.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }

    @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.GREED;
    }

    @Override
    public LevelScreenTypes getCurrentScreen() {
        return LevelScreenTypes.ENVY;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.ENVY;

    }
}