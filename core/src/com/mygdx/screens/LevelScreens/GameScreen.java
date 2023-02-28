package com.mygdx.screens.LevelScreens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.Items.Coin;
import com.mygdx.objects.Items.Item;
import com.mygdx.objects.player.Player;


public abstract class GameScreen implements Screen {
    private static GameScreen INSTANCE = null;

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
    protected boolean COMPLETED_LEVEL;
    protected boolean COLLECTED_ALL_ITEMS;
    protected boolean WIN_LEVEL;
    protected boolean LOSE_LEVEL;
    private float timeSeconds = 0f;
    private float period = 2.8f;
    int recoverycooldown=0;
    World world;
    private ArrayList<Coin> coins; 
    protected Item item0;
    protected Item item1;
    protected Item item2;
    BitmapFont font24 ;
    // Objects
    //protected Player player;
    public static Player player;
    public GameScreen() {
        INSTANCE = this;
        camera = new OrthographicCamera(16, 9);
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        PAUSED = false;
        FIRSTPAUSED = false;
        COMPLETED_LEVEL = false;
        COLLECTED_ALL_ITEMS = false;
        WIN_LEVEL = false;
        LOSE_LEVEL = false;

        health_bar1=new Texture("HUD/greenbar (3).png");
        health_bar2=new Texture("HUD/greenbar (2).png");
        health_bar3=new Texture("HUD/greenbar (1).png");
        health_bar4=new Texture("HUD/greenbar (4).png");
        health_bar5=new Texture("HUD/greenbar (5).png");
       
        item_bar0=new Texture("HUD/Itembar(0).png");
        item_bar1= new Texture("HUD/Itembar(1).png");
        item_bar2= new Texture("HUD/Itembar(2).png");
        item_bar3= new Texture("HUD/Itembar(3).png");
        coins = new ArrayList<Coin>();
        
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rainyhearts.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.YELLOW;
        font24 = generator.generateFont(parameter);
        font24.setUseIntegerPositions(false); // font size 24 pixels
        generator.dispose();
 
        
        
    }

    public static Object getInstance() {    
        return INSTANCE;
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        
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
    public World getWorld() {
		return world;
	}

    public boolean getWin() {
        return WIN_LEVEL;
    }

    public void setWin(boolean won) {
        WIN_LEVEL = won;
    }

    public boolean getLose() {
        return LOSE_LEVEL;
    }

    public void setLose(boolean lost) {
        LOSE_LEVEL = lost;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    } 
    public void setCoin(Coin coin){
        this.coins.add(coin);
    }
    public void setItem0(Item item){
        this.item0 = item;
    }
    public void setItem1(Item item){
        this.item1 = item;
    }
    public void setItem2(Item item){
        this.item2 = item;
    }
    public Item getItem0(){
        return this.item0;
    }
    public Item getItem1(){
        return this.item1;
    }
    public Item getItem2(){
        return this.item2;
    }
    public ArrayList<Coin> getCoinList(){
        return coins;
    }
    public abstract LevelScreenTypes getCurrentScreen();

    public abstract LevelScreenTypes getNextScreen();
}