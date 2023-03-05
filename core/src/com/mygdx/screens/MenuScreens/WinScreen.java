package com.mygdx.screens.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.DemoScreenTypes;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;
import com.mygdx.screens.LevelScreens.GameScreen;

public class WinScreen extends Menu{

    private Texture background;
    private Texture indulgeMore;

    // Buttons
    private ImageButton playButton;
    private Texture playText;
    private ImageButton menuButton;
    private Texture menuText;

    private LevelScreenTypes curScreen;


    public WinScreen() {
        this.curScreen = ((GameScreen) GameScreen.getInstance()).getCurrentScreen();
        background = new Texture("titleScreen/titleBackground.png");
        indulgeMore = new Texture("titleScreen/indulgemore.png");

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);   
        // Batch for background
        batch.begin();
        batch.draw(background, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        batch.draw(indulgeMore, Constants.WINDOW_WIDTH / 5, Constants.WINDOW_HEIGHT / 2f);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        textbatch.begin();
        textbatch.draw(menuText, (Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        textbatch.draw(playText, (Constants.WINDOW_WIDTH - playButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
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

        playButton = new ImageButton(buttTextureRegionDrawable);
        playButton.setPosition((Constants.WINDOW_WIDTH - playButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
        playButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {                
                ((Indulge) Indulge.getInstance()).change_demo(DemoScreenTypes.DEMO_JUMP);
            }
        });
        playText = new Texture("titleScreen/play.png");

        stage.addActor(menuButton);
        stage.addActor(playButton);
    }

}
