package com.mygdx.screens.LevelScreens;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.MovingObstacles.Peppermint;
import com.mygdx.objects.player.Player;

public class LevelGluttony extends GameScreen {

    public LevelGluttony() {
        constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerLeftOffset = (camera.position.x * 100) - player.getX();
        playerSpeed = player.getSpeed();
        mintLinearVel = new Vector2(0,0);
        
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/gluttony00.png");
        backgrounds[1] = new Texture("backgrounds/gluttony01.png");
        backgrounds[2] = new Texture("backgrounds/gluttony02.png");
        
        // Set music
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/Clown.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }

    public void updatePeppermint() {
        // Getting peppermint array from tile map helper 
        for(Peppermint p: tileMapHelper.getPeppermint()) {
            p.update(1f);           
            //When player's x position is beyond a certain range, active the body
             // + (value)f , when value is smaller lands faster, larger then lands later
            if(p.getX() < player.getPositionX() + 250f / Constants.PPM) {
                //Set true to be active when in range
                p.getMintBody().setActive(true);
                p.setActivng(true);
            }
            // If id = 1, first pepper mint
            if(p.getID() == 1) {
                if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                        p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
                        player.setRespawn(false);
                    }
                }
            }
            //If id =2, second pepper mint
            if(p.getID() == 2) {
                if(player.getPositionX() > 95f && player.getPositionX() < 131.8f) {
                    if(player.RESPAWN) {            				
                        p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
                        player.setRespawn(false);
                    }
                }
            }
        }
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
        return LevelScreenTypes.GLUTTONY;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.SLOTH;
    }
}