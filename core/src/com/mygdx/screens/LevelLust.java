package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Levels;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.Player;

public class LevelLust extends GameScreen {

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float bgMaxScrollingSpeed;

    // Objects
    private Player player;
    private Music music;
    
    //Tiled Map
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper=new TileMapHelper(this);

    public LevelLust() {

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");
        // Set background scrolling speed
        bgMaxScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 5;
        orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        this.batch = new SpriteBatch();
        // Set music
        music=Gdx.audio.newMusic(Gdx.files.internal("Music/spy-jazz-20925.mp3"));
        music.setLooping(true);
        music.play();

    }
    
    @Override
    public void render(float deltaTime) {
    	world.step(1/60f,6, 2);
    	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(13, 0, 0);
		}
    	camera.update();
    	batch.setProjectionMatrix(camera.combined);
    	orthogonalTiledMapRenderer.setView(camera);
    	
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);
        // Change screens with user input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((Indulge) Indulge.getInstance()).change_screen(Levels.GLUTTONY);
        }
        batch.setProjectionMatrix(this.camera.combined);
        batch.begin();
        /*batch.draw(backgrounds[0], -backgroundOffsets[0], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        batch.draw(backgrounds[0], -backgroundOffsets[0] + Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        batch.draw(backgrounds[1], -backgroundOffsets[1], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        batch.draw(backgrounds[1], -backgroundOffsets[1] + Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        batch.draw(backgrounds[2], -backgroundOffsets[2], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        batch.draw(backgrounds[2], -backgroundOffsets[2] + Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);*/
        // Scrolling background
        renderBackground(deltaTime);

        batch.end();
        camera.update(true);
        orthogonalTiledMapRenderer.render();
        
    }

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * bgMaxScrollingSpeed / 6; 
        backgroundOffsets[1] += deltaTime * bgMaxScrollingSpeed / 4; 
        backgroundOffsets[2] += deltaTime * bgMaxScrollingSpeed / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 2 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 3 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 4 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 5 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 6 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 7 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 8 * Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);

        }
    }
    
    @Override
    public void resize(int width, int height) {
    	camera.position.set(width/2f, height/2f, 0);
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
        
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {
        
    }
    
    @Override
    public void hide() {
        
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    } 
}
