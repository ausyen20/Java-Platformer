package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;


public abstract class GameScreen implements Screen {

    // Screen
    protected OrthographicCamera camera;
    protected Viewport viewport;

    // Background
    private float bgMaxScrollingSpeed;

    // Graphics
    //protected World world;
    protected SpriteBatch batch;
    protected SpriteBatch front_batch;
    protected Texture[] backgrounds;
    protected float w = Gdx.graphics.getWidth();
	protected float h = Gdx.graphics.getHeight();

    // Buttons
    protected Stage stage;
    protected TextureRegion buttTextureRegion;
    protected TextureRegionDrawable buttTextureRegionDrawable;
    protected ImageButton menuButton;
    protected Texture menuText;

    // Objects
    //protected Player player;
    //protected Music music;

    public GameScreen() {
        camera = new OrthographicCamera(16, 9);
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        //bgMaxScrollingSpeed = (float) (Constants.WORLD_WIDTH) / 5;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public abstract void render(float delta);   
    

    @Override
    public void resize(int width, int height) {
        camera.position.set(width/2f, height/2f, 0);
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void show() {
        buttTextureRegion = new TextureRegion(new Texture("titleScreen/button1.png"));
        buttTextureRegionDrawable = new TextureRegionDrawable(buttTextureRegion);

        menuButton = new ImageButton(buttTextureRegionDrawable);
        menuButton.setPosition((Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        menuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.TITLE);
            }
        });

        stage.addActor(menuButton);
        Gdx.input.setInputProcessor(stage);
    }    

    public void setScrollingSpeed(float newSpeed) {
        bgMaxScrollingSpeed = newSpeed;
    }

    public float getScrollingSpeed() {
        return bgMaxScrollingSpeed;
    }
}