package com.mygdx.screens.LevelScreens;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;

public class LevelGreed extends GameScreen {
		    
    public LevelGreed() {
        playerSpeed = player.getSpeed() * 1.3f;
        constantScrollingSpeed = playerSpeed / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerLeftOffset = (camera.position.x * 77) - player.getX();
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/greed00.png");
        backgrounds[1] = new Texture("backgrounds/greed01.png");
        backgrounds[2] = new Texture("backgrounds/greed02.png");
        
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/OST 2 - Business As Usual.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }

     public Player getPlayer() {
     	return player;
     }

     @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.SLOTH;
    }

     @Override
     public LevelScreenTypes getCurrentScreen() {
     	return LevelScreenTypes.GREED;
     }

     @Override
     public LevelScreenTypes getNextScreen() {
         return LevelScreenTypes.ENVY;
     }


 
}