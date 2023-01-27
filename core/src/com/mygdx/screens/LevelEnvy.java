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

public class LevelEnvy extends GameScreen {

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float layoutScrollingSpeed;

    public LevelEnvy() {

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/envy00.png");
        backgrounds[1] = new Texture("backgrounds/envy01.png");
        backgrounds[2] = new Texture("backgrounds/envy02.png");

        layoutScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 4;

        batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            ((Indulge) Indulge.getInstance()).change_screen(Levels.LUST);
        }
        batch.begin();
        // Scrolling background
        renderBackground(deltaTime);
        batch.end();
    }

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * layoutScrollingSpeed / 4; 
        backgroundOffsets[1] += deltaTime * layoutScrollingSpeed / 2; 
        backgroundOffsets[2] += deltaTime * layoutScrollingSpeed; 

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0, Constants.ASSET_BACKGROUND_WIDTH, Constants.WORLD_HEIGHT);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + Constants.ASSET_BACKGROUND_WIDTH, 0, 3300, Constants.WORLD_HEIGHT);
        }
    }
}
