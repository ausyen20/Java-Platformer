package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Levels;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.WorldContactListener;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.player.Player;


public class LevelLust extends GameScreen {

    private Texture[] backgrounds;
    private float w = Gdx.graphics.getWidth();
	private float h = Gdx.graphics.getHeight();

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    //private float bgMaxScrollingSpeed;
    
    //Tiled Map
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    protected Box2DDebugRenderer box2DDebugRenderer;

    private Player player;
    private World world;

    public LevelLust() {
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,-20f),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        world.setContactListener(new WorldContactListener());
        // Set background texture
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");
        // Set scrolling speed
        //bgMaxScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 5;

        // Set music
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/spy-jazz-20925.mp3"));
        music.setLooping(true);
        music.play();     
    }
    
    @Override
    public void render(float deltaTime) {
        this.update();
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

    	world.step(1/60f,6, 2);
    	
    	camera.update();
    	batch.setProjectionMatrix(camera.combined);
    	orthogonalTiledMapRenderer.setView(camera);
        
        // Change screens with user input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((Indulge) Indulge.getInstance()).change_screen(Levels.GLUTTONY);
        }

        batch.setProjectionMatrix(this.camera.combined);
        batch.begin();
        // Scrolling background
        renderBackground(deltaTime);
        player.render(batch);
        batch.end();
        orthogonalTiledMapRenderer.render();
        camera.update(true);
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

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
		cameraUpdate();
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);	
		player.update();
	}

    private void cameraUpdate() {
		//Camera center to the player obj
		Vector3 position = camera.position;
		position.x = Math.round((player.getBody().getPosition().x  * Constants.PPM * 10) / 10f) + Constants.WORLD_WIDTH / 3;
        position.y = Constants.WORLD_HEIGHT / 2;
		camera.position.set(position);   
        setScrollingSpeed(player.getLinearVelocity() * 100);
        if (position.x >= Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2) {
            position.x = Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2;
            setScrollingSpeed(0);
        }
        else camera.update();
	}

    public World getWorld() {
		return world;
	}
        
    public void setPlayer(Player player) {
        this.player = player;
    }    
}