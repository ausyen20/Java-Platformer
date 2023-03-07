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

public class DemoCoins extends DemoScreen {

    private float elapsedtime;
    
    public DemoCoins() {
        // Add demo assets
        //backgroundText = new Texture("demos/lustdemoText.png");
        backgroundText = new Texture("demos/coinsdemotext.png");
        characterImage = new Texture("demos/coinsdemo-sheet.png");
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
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.GREED);
        }
        batch.begin();
        batch.draw(backgroundText, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.draw(animation.getKeyFrame(elapsedtime, true), 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.end();
    }

}
