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

public class DemoItem extends DemoScreen {

    private float elapsedtime;
    
    public DemoItem() {
        // Add demo assets
        //backgroundText = new Texture("demos/lustdemoText.png");
        backgroundText = new Texture("demos/demo2Text.png");
        characterImage = new Texture("demos/demo2anim1-sheet.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(characterImage, 320, 180);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            animationFrames[index++] = tmpFrames[0][i];
        }
        animation = new Animation<TextureRegion>(1f/2f, animationFrames);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        elapsedtime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.LUST);
        }
        batch.begin();
        batch.draw(backgroundText, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.draw(animation.getKeyFrame(elapsedtime, true), 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.end();
    }

}

