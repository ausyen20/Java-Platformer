package com.mygdx.indulge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelEnvy implements Screen {

    // Screen
    private Camera camera;
    private Viewport viewport;

    // Graphics
    private SpriteBatch batch;
    private Texture[] backgrounds;

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float bgMaxScrollingSpeed;

    // World parameters
    private final int WORLD_WIDTH = 1600;
    private final int WORLD_HEIGHT = 900;

    LevelEnvy() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("envy00.png");
        backgrounds[1] = new Texture("envy01.png");
        backgrounds[2] = new Texture("envy02.png");

        bgMaxScrollingSpeed = (float) (WORLD_WIDTH) / 4;

        batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            ((Indulge) Indulge.getInstance()).change_screen(Levels.LUST);
        }
        batch.begin();

        // Scrolling background
        renderBackground(deltaTime);

        batch.end();
        
    }

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * bgMaxScrollingSpeed / 4; 
        backgroundOffsets[1] += deltaTime * bgMaxScrollingSpeed / 2; 
        backgroundOffsets[2] += deltaTime * bgMaxScrollingSpeed; 

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > 3300) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0, 3300, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + 3300, 0, 3300, WORLD_HEIGHT);
        }
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
    
}
