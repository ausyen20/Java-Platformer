package com.mygdx.screens.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;

public class Options extends Menu {

    // Batches
    private SpriteBatch batch;
    private SpriteBatch textbatch;

    // Background texture
    private Texture background;

    // Button
    private ImageButton returnButton;
    private Texture returnText;
    /*
     * Normal mode = can't skip through levels without completing the other
     * Assist mode = can change to any screen with shortcuts
     */
    private ImageButton normalButton;
    private Texture normalText;
    private ImageButton assistButton;    
    private Texture assistText;

    public Options() {
        background = new Texture("titleScreen/titleBackground.png");
        batch = new SpriteBatch();
        textbatch = new SpriteBatch();
    }
    
    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1); 
        
        // Batch for background
        batch.begin();
        batch.draw(background, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.end();
        
        // Draw stage (buttons)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        // Batch for text on buttons
        textbatch.begin();
        textbatch.draw(returnText, (Constants.WINDOW_WIDTH - returnButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        textbatch.end();
    }

    public void show() {
        super.show();

        returnButton = new ImageButton(buttTextureRegionDrawable);
        returnButton.setPosition((Constants.WINDOW_WIDTH - returnButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.TITLE);
            }
        });
        returnText = new Texture("titleScreen/return.png");

        stage.addActor(returnButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        background.dispose();
        textbatch.dispose();
        returnText.dispose();
        stage.dispose();
    }
}
