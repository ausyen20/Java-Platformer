package com.mygdx.screens.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.helpers.Modes;
import com.mygdx.indulge.Indulge;

public class Options extends Menu {

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
    private BitmapFont font24;
    private Label controls;
    private Group controlsGroup;

    public Options() {
        background = new Texture("titleScreen/titleBackground.png");
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
        textbatch.draw(returnText, (Constants.WINDOW_WIDTH - returnButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 7);
        textbatch.draw(normalText, Constants.WINDOW_WIDTH / 10, Constants.WINDOW_HEIGHT / 1.5f);
        textbatch.draw(assistText, Constants.WINDOW_WIDTH / 10, Constants.WINDOW_HEIGHT / 2.5f);
        textbatch.end();

        if (((Modes) Modes.getInstance()).getAssist()) {
            controls.setText(String.format("Assist mode allows you to switch between\n" + 
                                            "levels without having to complete them.\n\n" +
                                            "Controls:\n" + 
                                            "- Press SPACE to jump\n" + 
                                            "- Press SPACE to skip cutscenes\n" +
                                            "- Press ESCAPE to pause/unpause game\n" +
                                            "- Press A to switch to next level\n" +
                                            "- Press S to switch to previous level"));
        } else {
            controls.setText(String.format("Controls:\n" + 
                                            "- Press SPACE to jump\n" + 
                                            "- Press SPACE to skip cutscenes\n" +
                                            "- Press ESCAPE to pause/unpause game"));
        }
    }

    public void show() {
        super.show();

        returnButton = new ImageButton(buttTextureRegionDrawable);
        returnButton.setPosition((Constants.WINDOW_WIDTH - returnButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 7);
        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.TITLE);
            }
        });
        returnText = new Texture("titleScreen/return.png");

        normalButton = new ImageButton(buttTextureRegionDrawable);
        normalButton.setPosition(Constants.WINDOW_WIDTH / 10, Constants.WINDOW_HEIGHT / 1.5f);
        normalButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Modes) Modes.getInstance()).setAssist(false);
            }
        });
        normalText = new Texture("titleScreen/normal.png");

        assistButton = new ImageButton(buttTextureRegionDrawable);
        assistButton.setPosition(Constants.WINDOW_WIDTH / 10, Constants.WINDOW_HEIGHT / 2.5f);
        assistButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Modes) Modes.getInstance()).setAssist(true);
            }
        });
        assistText = new Texture("titleScreen/assist.png");

        controls();
        stage.addActor(returnButton);
        stage.addActor(normalButton);
        stage.addActor(assistButton);
    }

    public void controls() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rainyhearts.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        font24 = generator.generateFont(parameter);
        font24.setUseIntegerPositions(false);
        generator.dispose();
        
        controls = new Label(String.format("Controls:\n" + 
                                            "- Press SPACE to jump\n" + 
                                            "- Press SPACE to skip cutscenes\n" +
                                            "- Press ESCAPE to pause/unpause game"), 
                         new Label.LabelStyle(font24,Color.WHITE));
        stage.addActor(controls);
        controlsGroup = new Group();
        controlsGroup.addActor(controls);
        stage.addActor(controlsGroup);
        controlsGroup.setScale(3f, 3f);
        controlsGroup.setPosition(Constants.WORLD_WIDTH + 300, Constants.WINDOW_HEIGHT - 500);
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
