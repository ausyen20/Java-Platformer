package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.WorldContactListener;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.player.Player;
import com.mygdx.helpers.AudioManager;

public class LevelLust extends GameScreen {
	
    // Background 
    private Texture[] backgrounds;
    private float[] backgroundOffsets = {0, 0, 0};

    // Timing
    private float cameraScrollingSpeed;
    private float constantScrollingSpeed;
    private float playerOffsetX;
    private float playerSpeed;
    
    // Tiled Map
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    protected Box2DDebugRenderer box2DDebugRenderer;

    // Objects
   
    

    public LevelLust() {
        this.batch = new SpriteBatch();
        this.front_batch = new SpriteBatch();
        this.textbatch = new SpriteBatch();

        this.world = new World(new Vector2(0,-7f),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        world.setContactListener(new WorldContactListener());
        player.initPos();
        constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerOffsetX = (camera.position.x * 100) - player.getX();
        playerSpeed = player.getSpeed();

        // Set background texture
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");

        // Set music
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/spy-jazz-20925.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }
    
    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        super.pauseScreen();
        
        if (player.health<=0) {
        	((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.GLUTTONY);
        }
    	world.step(1/60f,6, 2);
        // if paused, set deltatime to 0 to stop background scrolling
        if (PAUSED || FIRSTPAUSED) {
            deltaTime = 0;
        } 
        
        camera.update(true);
        this.update();
        orthogonalTiledMapRenderer.setView(camera);

        // User input to change screens / pause
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.GLUTTONY);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !FIRSTPAUSED) {
            PAUSED = !PAUSED;
        }

        batch.setProjectionMatrix(this.camera.combined);
        front_batch.setProjectionMatrix(this.camera.combined);
        // batch for the background
        batch.begin();
        renderBackground(deltaTime);
        batch.end();
        orthogonalTiledMapRenderer.render();

        // batch for foreground (player, etc)
        front_batch.begin();
        front_batch.setColor(1,1,1,1f);
        if (player.recovery==true) {
        	front_batch.setColor(1,0,0,1f);
        	recoverycooldown++;
        	if (recoverycooldown>60) {
        		player.setRecovery(false);
        		recoverycooldown=0;
        	}
        	
        	
        }
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
        front_batch.draw(item_bar0,camera.position.x-160 , 50);
        front_batch.end();
        
        // Show back to menu button if game paused
        if (PAUSED) {
            super.drawButtons();
        } else Gdx.input.setInputProcessor(null);

        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    /*
     * Parallex effect for the background layers
     */
    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * getScrollingSpeed() / 6; 
        backgroundOffsets[1] += deltaTime * getScrollingSpeed() / 4; 
        backgroundOffsets[2] += deltaTime * getScrollingSpeed() / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            for (int i = 0; i < 2; i++) {
                batch.draw(backgrounds[layer],
                        -1 * backgroundOffsets[layer] + camera.position.x - camera.viewportWidth / 2 + (i * Constants.ASSET_BACKGROUND_WIDTH), 
                        0,
                        Constants.ASSET_BACKGROUND_WIDTH,
                        Constants.WORLD_HEIGHT);
            }
        }
    }

    private void update() {
		world.step(1/60f,6, 2);
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);	
        // If game is not paused, update camera and player
        if (!PAUSED && !FIRSTPAUSED) {
            cameraUpdate();
            player.update();
            acceleratePlayer();
            outOfScreen();
            player.setSpawnPoint();
            relocateCamera();
            world.setGravity(new Vector2(0, -7f));
        } else {
            player.getBody().setLinearVelocity(0, 0);
            world.setGravity(new Vector2(0, 0));
        }
	}

    private void acceleratePlayer() {
        if ((camera.position.x - player.getX()) > playerOffsetX + 2) {
            player.setSpeed(playerSpeed + 2);
        }
        else player.setSpeed(playerSpeed);
    }

    public void outOfScreen() {
        if ((camera.position.x - player.getX()) > playerOffsetX + playerOffsetX/2 + player.getWidth()) {
            player.setDead(true);
        }
    }

    private void relocateCamera() {
        if (player.getDead()) {
        	if (player.recovery==false){
             	player.health--;
             	System.out.println(player.health);
             }
            camera.position.x = player.getX() + playerOffsetX;
            player.setDead(false);
            player.setRecovery(true);
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
                ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.LUST);

            }
        });
    }

   
        
    
    public Player getPlayer() {
    	return player;
    }
    
}