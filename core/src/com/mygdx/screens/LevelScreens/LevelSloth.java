package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.WorldContactListener;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
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

    @Override
    public void render(float deltaTime) {
    	 // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        super.pauseScreen();
        
        if (player.health<=0) {
            setLose(true);
        	((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        }
    	world.step(1/60f,6, 2);
        // if paused, set deltatime to 0 to stop background scrolling
        if (PAUSED || FIRSTPAUSED) {
            deltaTime = 0;
        } 
        
        camera.update(true);
        this.update();
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.GREED);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !FIRSTPAUSED) {
            if (PAUSED) {
                player.getBody().setLinearVelocity(playerLinearVel);
            } else {
                playerLinearVel = player.getBody().getLinearVelocity();
            }
            PAUSED = !PAUSED;
        }

        bg_batch.setProjectionMatrix(this.camera.combined);
        front_batch.setProjectionMatrix(this.camera.combined);
        player_batch.setProjectionMatrix(this.camera.combined);

        // batch for the background
        bg_batch.begin();
        renderBackground(deltaTime);
        bg_batch.end();
        orthogonalTiledMapRenderer.render();

        // batch for foreground (player, etc)
        front_batch.begin();
        getCoinList().forEach((c) -> c.render(front_batch));
        getItem0().render(front_batch);
        getItem1().render(front_batch);
        getItem2().render(front_batch);
        player.render(front_batch);
        switch(player.health) {
        case 1:
        	front_batch.draw(health_bar1, camera.position.x-160, 50);
        	break;
        case 2:
        	front_batch.draw(health_bar2, camera.position.x-160, 50);
        	break;
        case 3:
        	front_batch.draw(health_bar3, camera.position.x-160, 50);
        	break;
        case 4:
        	front_batch.draw(health_bar4, camera.position.x-160, 50);
        	break;
        case 5:
        	front_batch.draw(health_bar5, camera.position.x-160, 50);
        	break;
        }
        switch(player.getItemsCollected()){
            
            case 0:
                 front_batch.draw(item_bar0,camera.position.x-160 , 50);
                 break;
            case 1:
                 front_batch.draw(item_bar1,camera.position.x-160 , 50);
                 break;
            case 2:
                 front_batch.draw(item_bar2,camera.position.x-160 , 50);
                 break;
            case 3:
                 front_batch.draw(item_bar3,camera.position.x-160 , 50);
                 break;
        }
        front_batch.end();

        player_batch.begin();
        player_batch.setColor(1,1,1,1f);
        if (player.recovery==true) {
        	player_batch.setColor(1,0,0,1f);
            if(!PAUSED) {
        	    recoverycooldown++;
        	    if (recoverycooldown>60) {
        		    player.setRecovery(false);
        		    recoverycooldown=0;
        	    }
            }
        	
        }
        player.render(player_batch);
        player_batch.end();
        
        coinCount.setText(String.format("%02d",player.getCoinsCollected()));
        coinGroup.setScale(5f, 5f);
        coinGroup.setPosition(Constants.WORLD_WIDTH + 1100, Constants.WINDOW_HEIGHT - 100);
        coinStage.draw();
        // Show back to menu button if game paused
        if (PAUSED) { 
            super.drawButtons(); 
        } else if (!LOSE_LEVEL && !WIN_LEVEL) {
            Gdx.input.setInputProcessor(null);
        } 


        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }
    
    private void update() {
		world.step(1/60f,6, 2);
		bg_batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);	
        // If game is not paused, update camera and player
        if (!PAUSED && !FIRSTPAUSED) {
            cameraUpdate();
            player.update();
            acceleratePlayer();
            outOfScreenLeft();
            winCondition();
            getWin();
            player.setSpawnPoint();
            relocateCamera();
            world.setGravity(new Vector2(0, -4f));
        } else {
            player.getBody().setLinearVelocity(0, 0);
            world.setGravity(new Vector2(0, 0f));
        }
	}

    private void cameraUpdate() {
        Vector3 position = camera.position;
		// Camera center to the player obj
		//position.x = Math.round((player.getBody().getPosition().x  * Constants.PPM * 10) / 10f) + Constants.WORLD_WIDTH / 3;
		position.x += cameraScrollingSpeed;
        position.y = Constants.WORLD_HEIGHT / 2;
		camera.position.set(position);   
        setScrollingSpeed(cameraScrollingSpeed * 100); // scrolling speed of the background matches the camera
        // If player is at the end of level, stop camera movement
        if (position.x >= Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2) {
            position.x = Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2;
            cameraScrollingSpeed = 0;
        } 
        else cameraScrollingSpeed = constantScrollingSpeed;
	}

    @Override
    public void show() {
        super.show();
        restartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.SLOTH);

            }
        });
    }

    public Player getPlayer() {
    	return player;
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
