package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;

public class LevelEnvy extends GameScreen {
	
    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float layoutScrollingSpeed;

    public LevelEnvy() {
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/envy00.png");
        backgrounds[1] = new Texture("backgrounds/envy01.png");
        backgrounds[2] = new Texture("backgrounds/envy02.png");
        // Set background scrolling speed
        layoutScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 4;
        bg_batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);
        // Change screens with user input
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.LUST);
        }
        bg_batch.begin();
        // Scrolling background
        renderBackground(deltaTime);
        bg_batch.end();
    }

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * layoutScrollingSpeed / 4; 
        backgroundOffsets[1] += deltaTime * layoutScrollingSpeed / 2; 
        backgroundOffsets[2] += deltaTime * layoutScrollingSpeed; 

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            for (int i = 0; i < 2; i++) {
                bg_batch.draw(backgrounds[layer],
                        -1 * backgroundOffsets[layer] + camera.position.x - camera.viewportWidth / 2 + (i * Constants.ASSET_BACKGROUND_WIDTH), 
                        0,
                        Constants.ASSET_BACKGROUND_WIDTH,
                        Constants.WORLD_HEIGHT);
            }
        }
    }


    @Override
    public LevelScreenTypes getCurrentScreen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentScreen'");
    }


    @Override
    public LevelScreenTypes getNextScreen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextScreen'");
    }
}
