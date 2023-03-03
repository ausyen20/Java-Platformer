package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.helpers.LevelScreenTypes;

public class LevelEnvy extends GameScreen {
	

    public LevelEnvy() {
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/envy00.png");
        backgrounds[1] = new Texture("backgrounds/envy01.png");
        backgrounds[2] = new Texture("backgrounds/envy02.png");
    }

    @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.GREED;
    }

    @Override
    public LevelScreenTypes getCurrentScreen() {
        return LevelScreenTypes.ENVY;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.ENVY;

    }
}