package com.mygdx.screens.MenuScreens;

import javax.swing.event.SwingPropertyChangeSupport;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;
import com.mygdx.screens.LevelScreens.GameScreen;

public class EndLevel extends Menu{
    // Buttons
    private ImageButton nextLevelButton;
    private Texture nextLevelText;
    private ImageButton restartButton;
    private Texture restartText;
    private ImageButton menuButton;
    private Texture menuText;
    private LevelScreenTypes curScreen;
    private LevelScreenTypes nextScreen;
    
    public EndLevel() {
        this.curScreen = ((GameScreen) GameScreen.getInstance()).getCurrentScreen();
        this.nextScreen = ((GameScreen) GameScreen.getInstance()).getNextScreen();
    }

    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(0.40f, 0.05f, 0.15f, 1);   
        // Draw stage (buttons)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // Batch for text on the buttons
        textbatch.begin();
        textbatch.draw(menuText, (Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        if (((GameScreen)GameScreen.getInstance()).getWin()) {
            textbatch.draw(nextLevelText, (Constants.WINDOW_WIDTH - nextLevelButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 2);
        }
        textbatch.draw(restartText, (Constants.WINDOW_WIDTH - restartButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
        textbatch.end();
    }

    @Override
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
        menuText = new Texture("titleScreen/menu.png");


        nextLevelButton = new ImageButton(buttTextureRegionDrawable);
        nextLevelButton.setPosition((Constants.WINDOW_WIDTH - nextLevelButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 2);
        nextLevelButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_levels(nextScreen);

            }
        });
        nextLevelText = new Texture("titleScreen/nextlevel.png");
        
        restartButton = new ImageButton(buttTextureRegionDrawable);
        restartButton.setPosition((Constants.WINDOW_WIDTH - restartButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
        restartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {                
                ((Indulge) Indulge.getInstance()).change_levels(curScreen);
            }
        });
        restartText = new Texture("titleScreen/restart.png");
        
        if (((GameScreen)GameScreen.getInstance()).getWin()) {
            stage.addActor(nextLevelButton);
        }
        stage.addActor(menuButton);
        stage.addActor(restartButton);
    }    

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        textbatch.dispose();
        restartText.dispose();
        menuText.dispose();
        stage.dispose();
    }
}
