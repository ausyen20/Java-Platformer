package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Levels;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.Player;

public class LevelLust extends GameScreen {

    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    private float bgMaxScrollingSpeed;

    // Objects
    private Player player;

    public LevelLust() {
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/lust00.png");
        backgrounds[1] = new Texture("backgrounds/lust01.png");
        backgrounds[2] = new Texture("backgrounds/lust02.png");
        // Set background scrolling speed
        bgMaxScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 5;
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
        batch.end();
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
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
}
