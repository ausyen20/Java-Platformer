package com.mygdx.screens.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.DemoScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;
import com.mygdx.helpers.AudioManager;

public class TitleScreen extends Menu{

    // Background textures
    private Texture background;
    private Texture indulgeTitle;
    
    // Buttons
    private ImageButton playButton;
    private Texture playText;
    private ImageButton optionsButton;
    private Texture optionsText;
    private ImageButton quitButton;
    private Texture quitText;

    public TitleScreen() {
        background = new Texture("titleScreen/titleBackground.png");
        indulgeTitle = new Texture("titleScreen/indulgeTitle.png");

        // If the same music isn't already playing, set and play music.
        if(!((AudioManager) AudioManager.getInstance()).isAlreadyPlaying("Music/deviation-130965.mp3")) {
            ((AudioManager) AudioManager.getInstance()).setMusic("Music/deviation-130965.mp3");
            ((AudioManager) AudioManager.getInstance()).playMusic();
        }
    }

    public void userInput() {
        playButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_demo(DemoScreenTypes.DEMO_LUST);
            }
        });
        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.OPTIONS);
            }
        });
        quitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
    }

    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);   
        
        // Batch for background
        batch.begin();
        batch.draw(background, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.draw(indulgeTitle, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.end();

        // Draw stage (buttons)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // Batch for text on the buttons
        textbatch.begin();
        textbatch.draw(playText, Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 2 );
        textbatch.draw(optionsText, Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 3 );
        textbatch.draw(quitText, Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 6 );
        textbatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ((Indulge) Indulge.getInstance()).change_demo(DemoScreenTypes.DEMO_LUST);
        }
    }

    @Override
    public void show() {
        super.show();
        // play button
        playButton = new ImageButton(buttTextureRegionDrawable);
        playButton.setPosition(Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 2);
        playText = new Texture("titleScreen/play.png");
        // options button
        optionsButton = new ImageButton(buttTextureRegionDrawable);
        optionsButton.setPosition(Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 3);
        optionsText = new Texture("titleScreen/options.png");
        // quit button
        quitButton = new ImageButton(buttTextureRegionDrawable);
        quitButton.setPosition(Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 6);
        quitText = new Texture("titleScreen/quit.png");

        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
        
        userInput();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        background.dispose();
        indulgeTitle.dispose();
        textbatch.dispose();
        playText.dispose();
        optionsText.dispose();
        quitText.dispose();
        stage.dispose();        
    }
}

