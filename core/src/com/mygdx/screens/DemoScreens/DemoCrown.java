package com.mygdx.screens.DemoScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.indulge.Indulge;

public class DemoCrown extends DemoScreen{

    private float elapsedtime;

    public DemoCrown() {
        backgroundText = new Texture("demos/crowndemotext.png");
        characterImage = new Texture("obstacles/shadowkingsheet.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(characterImage, 40, 40);
        animationFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            animationFrames[i] = tmpFrames[1][i];
        }
        animation = new Animation<TextureRegion>(1f/13f, animationFrames);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        elapsedtime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.ENVY);
        }
        batch.begin();
        batch.draw(backgroundText, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.draw(animation.getKeyFrame(elapsedtime, true),Constants.WINDOW_WIDTH/2 - 200, Constants.WINDOW_HEIGHT/2 - 200, 400, 400);
        batch.end();
    }
}   
