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
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
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
    private float timeSeconds = 0f;
    private float period = 2.8f;
    private float cameraScrollingSpeed;
    
    // Tiled Map
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    protected Box2DDebugRenderer box2DDebugRenderer;

    // Objects
    private Player player;
    private World world;

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
        cameraScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));

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

        // Pause screen for the first few seconds
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds < period){
            FIRSTPAUSED = true;
        } else FIRSTPAUSED = false;

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
        player.render(front_batch);
        front_batch.end();
        
        // Show back to menu button if game paused
        if (PAUSED) {
            Gdx.input.setInputProcessor(stage);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
            textbatch.begin();
            textbatch.draw(menuText, (Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3.5f);
            textbatch.draw(resumeText, (Constants.WINDOW_WIDTH - resumeButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 2);
            textbatch.end();
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
            world.setGravity(new Vector2(0, -7f));
        } else {
            player.getBody().setLinearVelocity(0, 0);
            world.setGravity(new Vector2(0, 0));
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
            setScrollingSpeed(0);
        }
        else camera.update();
	}

    @Override
    public void show() {
        super.show();
    }

    public World getWorld() {
		return world;
	}
        
    public void setPlayer(Player player) {
        this.player = player;
    }    
}