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
    private SpriteBatch batch;
    private SpriteBatch textbatch;

    private Texture background;
    private ImageButton menuButton;
    private Texture returnText;

    public Options() {
        background = new Texture("titleScreen/titleBackground.png");
        batch = new SpriteBatch();
        textbatch = new SpriteBatch();
    }
    
    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1); 
        
        batch.begin();
        batch.draw(background, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.end();
        
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        textbatch.begin();
        textbatch.draw(returnText, (Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        textbatch.end();
    }

    public void show() {
        super.show();

        menuButton = new ImageButton(buttTextureRegionDrawable);
        menuButton.setPosition((Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        menuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.TITLE);
            }
        });
        returnText = new Texture("titleScreen/return.png");

        stage.addActor(menuButton);
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
