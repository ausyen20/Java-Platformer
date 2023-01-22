package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Levels;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.Player;

public class LevelLust implements Screen {

    // Screen
    private Camera camera;
    private Viewport viewport;

    // Graphics
    private SpriteBatch batch;
    private Texture[] backgrounds;
    private Texture layout;

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float layoutOffset = 0;
    private float layoutScrollingSpeed;

    // Objecty
    private Player player;

    public LevelLust() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");
        layout = new Texture("layouts/layoutLust.png");

        layoutScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 5;

        batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);
        // Change screens with user input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((Indulge) Indulge.getInstance()).change_screen(Levels.GLUTTONY);
        }
        batch.begin();
        // Scrolling background
        renderBackground(deltaTime);
        // Scrolling layout
        renderLayout(deltaTime);
        batch.end();
    }

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * layoutScrollingSpeed / 6; 
        backgroundOffsets[1] += deltaTime * layoutScrollingSpeed / 4; 
        backgroundOffsets[2] += deltaTime * layoutScrollingSpeed / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + Constants.ASSET_BACKGROUND_WIDTH, 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
        }
    }

    private void renderLayout(float deltaTime) {
        /*
         * TODO:
         * the scrolling stops as soon as the layout is completed.
         * this may cause problems as the character only moves if the background keeps moving.
         * we might have to add more movement to the character towards the end so it keeps moving when the screen has stopped scrolling.
         */

        layoutOffset += deltaTime * layoutScrollingSpeed;
        if (layoutOffset > Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH) {
            layoutScrollingSpeed = 0;
        }
        batch.draw(layout, -layoutOffset, 0, Constants.ASSET_LAYOUT_WIDTH, Constants.WORLD_HEIGHT);
    }

    
    @Override
    public void resize(int width, int height) {
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
    
    @Override
    public void show() {
        
    }

    @Override
    public void dispose() {
        
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
}
