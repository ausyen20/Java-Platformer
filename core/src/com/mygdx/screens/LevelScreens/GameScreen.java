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
import com.mygdx.helpers.LevelScreenTypes;
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
    protected SpriteBatch textbatch;
    protected Texture[] backgrounds;
    protected float w = Gdx.graphics.getWidth();
	protected float h = Gdx.graphics.getHeight();

    // Buttons
    protected Stage stage;
    protected TextureRegion buttTextureRegion;
    protected TextureRegionDrawable buttTextureRegionDrawable;
    protected ImageButton restartButton;
    protected Texture restartText;
    protected ImageButton resumeButton;
    protected Texture resumeText;
    protected ImageButton menuButton;
    protected Texture menuText;

    //HUD- Health Bar and Collectables
    Texture health_bar1;
    Texture health_bar2;
    Texture health_bar3;
    Texture health_bar4;
    Texture health_bar5;
    Texture item_bar0;
    Texture item_bar1;
    Texture item_bar2;
    Texture item_bar3;

    protected boolean PAUSED;
    protected boolean FIRSTPAUSED;
    private float timeSeconds = 0f;
    private float period = 2.8f;

    // Objects
    //protected Player player;
    public GameScreen() {
        camera = new OrthographicCamera(16, 9);
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        PAUSED = false;
        FIRSTPAUSED = false;

        health_bar1=new Texture("HUD/greenbar (3).png");
        health_bar2=new Texture("HUD/greenbar (2).png");
        health_bar3=new Texture("HUD/greenbar (1).png");
        health_bar4=new Texture("HUD/greenbar (4).png");
        health_bar5=new Texture("HUD/greenbar (5).png");
        item_bar0=new Texture("HUD/Itembar(0).png");
        item_bar1= new Texture("HUD/Itembar(1).png");
        item_bar2= new Texture("HUD/Itembar(2).png");
        item_bar3= new Texture("HUD/Itembar(3).png");
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
        menuText = new Texture("titleScreen/menu.png");


        resumeButton = new ImageButton(buttTextureRegionDrawable);
        resumeButton.setPosition((Constants.WINDOW_WIDTH - resumeButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 2);
        resumeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PAUSED = false;
            }
        });
        resumeText = new Texture("titleScreen/resume.png");

        restartButton = new ImageButton(buttTextureRegionDrawable);
        restartButton.setPosition((Constants.WINDOW_WIDTH - restartButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
        restartText = new Texture("titleScreen/restart.png");

        stage.addActor(menuButton);
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
    }    

    public void drawButtons() {
        Gdx.input.setInputProcessor(stage);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        textbatch.begin();
        textbatch.draw(menuText, (Constants.WINDOW_WIDTH - menuButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 6);
        textbatch.draw(resumeText, (Constants.WINDOW_WIDTH - resumeButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 2);
        textbatch.draw(restartText, (Constants.WINDOW_WIDTH - resumeButton.getWidth()) / 2, Constants.WINDOW_HEIGHT / 3);
        textbatch.end();
    }

    public void pauseScreen() {
        // Pause screen for the first few seconds
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds < period){
            FIRSTPAUSED = true;
        } else FIRSTPAUSED = false;
    }

    public void setScrollingSpeed(float newSpeed) {
        bgMaxScrollingSpeed = newSpeed;
    }

    public float getScrollingSpeed() {
        return bgMaxScrollingSpeed;
    }
}