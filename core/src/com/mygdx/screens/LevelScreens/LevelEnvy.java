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
