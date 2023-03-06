package com.mygdx.screens.LevelScreens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.WorldContactListener;
import com.mygdx.helpers.Modes;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.Items.Coin;
import com.mygdx.objects.Items.Item;
import com.mygdx.objects.player.Player;


public abstract class GameScreen implements Screen {
    private static GameScreen INSTANCE = null;
    private LevelScreenTypes prevScreen;
    private LevelScreenTypes curScreen;
    private LevelScreenTypes nextScreen;

    // Screen
    protected OrthographicCamera camera;
    protected Viewport viewport;

    // Tiled Map
    protected OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    protected TileMapHelper tileMapHelper;
    protected Box2DDebugRenderer box2DDebugRenderer;

    // Timing
    protected float cameraScrollingSpeed;
    protected float constantScrollingSpeed;
    protected float playerLeftOffset;
    protected float playerSpeed;
    protected Vector2 playerLinearVel;
    protected Vector2 mintLinearVel;

    // Background
    private float bgMaxScrollingSpeed;

    // Graphics
    protected SpriteBatch bg_batch;
    protected SpriteBatch front_batch;
    protected SpriteBatch player_batch;
    protected SpriteBatch textbatch;
    protected Texture[] backgrounds;
    protected float[] backgroundOffsets = {0, 0, 0};
    protected float w = Gdx.graphics.getWidth();
	protected float h = Gdx.graphics.getHeight();

    // Pause Screen Buttons
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
    protected Label readygo;
    protected Group readygoGroup;

    // Objects
    public static Player player;
    protected World world;
    protected ArrayList<Coin> coins; 
    protected Item item0;
    protected Item item1;
    protected Item item2;
    BitmapFont font24;
    protected Label coinCount;
    protected Stage coinStage;
    protected Group coinGroup;

    protected GameScreen() {
        INSTANCE = this;
        this.prevScreen = ((GameScreen) GameScreen.getInstance()).getPreviousScreen();
        this.curScreen = ((GameScreen) GameScreen.getInstance()).getCurrentScreen();
        this.nextScreen = ((GameScreen) GameScreen.getInstance()).getNextScreen();

        bg_batch = new SpriteBatch();
        front_batch = new SpriteBatch();
        player_batch = new SpriteBatch();
        textbatch = new SpriteBatch();
        
        coins = new ArrayList<Coin>();

        world = new World(new Vector2(0,-7f),false);
        box2DDebugRenderer = new Box2DDebugRenderer();
        tileMapHelper = new TileMapHelper(this);
        orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        world.setContactListener(new WorldContactListener());

        backgrounds = new Texture[3];
        player.initPos();
        playerLinearVel = new Vector2(0,0);
        mintLinearVel = new Vector2(0,0);

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
        
        coinStage = new Stage(new ScreenViewport());
        coinCount = new Label(String.format("%d", player.getCoinsCollected()), new Label.LabelStyle(font24,Color.WHITE));
        coinStage.addActor(coinCount);
        coinGroup = new Group();
        coinGroup.addActor(coinCount);
        coinStage.addActor(coinGroup);

        readygo = new Label(String.format("ready?"), new Label.LabelStyle(font24,Color.WHITE));
        readygoGroup = new Group();
        readygoGroup.addActor(readygo);
        coinStage.addActor(readygoGroup);
    }

    public static Object getInstance() {    
        return INSTANCE;
    }    

    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        pauseScreen();
        
        if (player.health<=0) {
            setLose(true);
        	((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        }
    	world.step(1/60f,6, 2);
        // if paused, set deltatime to 0 to stop background scrolling
        if (PAUSED || FIRSTPAUSED) {
            deltaTime = 0;
        } 

        if (FIRSTPAUSED) {
            timeSeconds += Gdx.graphics.getDeltaTime();
            if(timeSeconds < period - 0.8f){
                readygo.setText("ready?");
            } else if(timeSeconds >= period - 0.8f) {
                readygo.setText("go!");
            }
            readygoGroup.setScale(5f, 5f);
            readygoGroup.setPosition(Constants.WORLD_WIDTH + 350, Constants.WINDOW_HEIGHT - 500);
        } else readygoGroup.remove();
        
        camera.update(true);
        this.update();
        orthogonalTiledMapRenderer.setView(camera);

        // User input to change screens / pause
        // If in assist mode, use A and S to change screens
        if (((Modes) Modes.getInstance()).getAssist()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.A) && curScreen != LevelScreenTypes.ENVY) {
                ((Indulge) Indulge.getInstance()).change_levels(nextScreen);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.S) && curScreen != LevelScreenTypes.LUST) {
                ((Indulge) Indulge.getInstance()).change_levels(prevScreen);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !FIRSTPAUSED) {
            if (PAUSED) {
                player.getBody().setLinearVelocity(playerLinearVel);
                if (curScreen == LevelScreenTypes.GLUTTONY) {
                    tileMapHelper.getPeppermint().forEach((c) -> c.getMintBody().setLinearVelocity(mintLinearVel));
                }
            } else {
                playerLinearVel = player.getBody().getLinearVelocity();
                if (curScreen == LevelScreenTypes.GLUTTONY) {
                    tileMapHelper.getPeppermint().forEach((c) -> mintLinearVel = c.getMintBody().getLinearVelocity());
                }
            }
            PAUSED = !PAUSED;
        }

        bg_batch.setProjectionMatrix(this.camera.combined);
        front_batch.setProjectionMatrix(this.camera.combined);
        player_batch.setProjectionMatrix(this.camera.combined);
        // batch for the background
        bg_batch.begin();
        renderBackground(deltaTime);
        bg_batch.end();
        orthogonalTiledMapRenderer.render();
        // batch for foreground (coins, items, layout...)
        front_batch.begin();
        getCoinList().forEach((c) -> c.render(front_batch));
        if((curScreen != LevelScreenTypes.GREED)) {
            getItem0().render(front_batch);
            getItem1().render(front_batch);
            getItem2().render(front_batch);
        }

        if (curScreen == LevelScreenTypes.GLUTTONY) {
            tileMapHelper.getPeppermint().forEach((c) -> c.render(front_batch));
        }
        
        switch(player.health) {
            case 1:
                front_batch.draw(health_bar1, camera.position.x-160, 50);
                break;
            case 2:
                front_batch.draw(health_bar2, camera.position.x-160, 50);
                break;
            case 3:
                front_batch.draw(health_bar3, camera.position.x-160, 50);
                break;
            case 4:
                front_batch.draw(health_bar4, camera.position.x-160, 50);
                break;
            case 5:
                front_batch.draw(health_bar5, camera.position.x-160, 50);
                break;
            default:
                break;
        }
        switch(player.getItemsCollected()){
            case 0:
                front_batch.draw(item_bar0,camera.position.x-160 , 50);
                break;
            case 1:
                front_batch.draw(item_bar1,camera.position.x-160 , 50);
                break;
            case 2:
                front_batch.draw(item_bar2,camera.position.x-160 , 50);
                break;
            case 3:
                front_batch.draw(item_bar3,camera.position.x-160 , 50);
                break;
            default:
                break;
        }
        front_batch.end();

        // batch for the player
        player_batch.begin();
        player_batch.setColor(1,1,1,1f);
        if (player.recovery) {
        	player_batch.setColor(1,0,0,1f);
            if(!PAUSED) {
        	    recoverycooldown++;
        	    if (recoverycooldown>60) {
        		    player.setRecovery(false);
        		    recoverycooldown=0;
        	    }
            }
        	
        }
        player.render(player_batch);
        player_batch.end();

        coinCount.setText(String.format("%02d",player.getCoinsCollected()));
        coinGroup.setScale(5f, 5f);
        coinGroup.setPosition(Constants.WORLD_WIDTH + 1100, Constants.WINDOW_HEIGHT - 100);
        coinStage.draw();
        // Show back to menu button if game paused
        if (PAUSED) { 
            drawButtons(); 
        } else if (!LOSE_LEVEL && !WIN_LEVEL) {
            Gdx.input.setInputProcessor(null);
        } 
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }
    

    @Override
    public void resize(int width, int height) {
        camera.position.set(width/2f, height/2f, 0);
        viewport.update(width, height, true);
        bg_batch.setProjectionMatrix(camera.combined);
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
        restartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_levels(curScreen);

            }
        });
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

    /*
     * Parallex effect for the background layers
     */
    public void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * getScrollingSpeed() / 6; 
        backgroundOffsets[1] += deltaTime * getScrollingSpeed() / 4; 
        backgroundOffsets[2] += deltaTime * getScrollingSpeed() / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            for (int i = 0; i < 2; i++) {
                bg_batch.draw(backgrounds[layer],
                        -1 * backgroundOffsets[layer] + camera.position.x - camera.viewportWidth / 2 + (i * Constants.ASSET_BACKGROUND_WIDTH), 
                        0,
                        Constants.ASSET_BACKGROUND_WIDTH,
                        Constants.WORLD_HEIGHT);
            }
        }
    }

    public void update() {
		world.step(1/60f,6, 2);
		bg_batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);	
        // If game is not paused, update camera and player
        if (!PAUSED && !FIRSTPAUSED) {
            cameraUpdate();
            player.update();
            acceleratePlayer();
            outOfScreenLeft();
            winCondition();
            getWin();
            switch (curScreen) {
                case LUST:
                    player.setSpawnsLust();
                    break;
                case GLUTTONY:
                    player.setSpawnsGluttony();
                    break;
                case SLOTH:
                    player.setSpawnsSloth();
                    break;
                case GREED:
                    // TEMPORARY!
                    player.setSpawnsGreed();
                    break;
                case ENVY:
                    // TEMPORARY!
                    player.setSpawnsEnvy();
                    break;
                default:
                    break;
            }
            relocateCamera();
            if (curScreen == LevelScreenTypes.SLOTH) {
                world.setGravity(new Vector2(0, -4f));
            } else world.setGravity(new Vector2(0, -7f));
            updatePeppermint();
            
            updateBoulder();
            
        } else {
            player.getBody().setLinearVelocity(0, 0);
            if (curScreen == LevelScreenTypes.GLUTTONY) {
                tileMapHelper.getPeppermint().forEach((c) -> c.getMintBody().setLinearVelocity(0, 0));
            }
            world.setGravity(new Vector2(0, 0f));
        }
        
	}


	

	public void cameraUpdate() {
        Vector3 position = camera.position;
		// Camera center to the player obj
		position.x += cameraScrollingSpeed;
        position.y = Constants.WORLD_HEIGHT / 2;
		camera.position.set(position);   
        setScrollingSpeed(cameraScrollingSpeed * 100); // scrolling speed of the background matches the camera
        // If player is at the end of level, stop camera movement
        if (position.x >= Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2) {
            position.x = Constants.ASSET_LAYOUT_WIDTH - Constants.WORLD_WIDTH / 2;
            cameraScrollingSpeed = 0;
        } 
        else cameraScrollingSpeed = constantScrollingSpeed;
	}

    public void acceleratePlayer() {
        if ((camera.position.x - player.getX()) > playerLeftOffset + 2) {
            player.setSpeed(playerSpeed + 2);
        }
        else player.setSpeed(playerSpeed);
    }

    public void outOfScreenLeft() {
        if ((camera.position.x - player.getX()) > playerLeftOffset + playerLeftOffset/2 + player.getWidth()) {
            player.setDead(true);
        }
    }

    public void outOfScreenRight() {
        if (player.getX() > Constants.ASSET_LAYOUT_WIDTH) {
            COMPLETED_LEVEL = true;
        }
    }

    public void collectedAllItems() {
        if(player.getItemsCollected() == 3) {
            COLLECTED_ALL_ITEMS = true;
        }
    }

    public void winCondition() {
        outOfScreenRight();
        collectedAllItems();
        if (COMPLETED_LEVEL && COLLECTED_ALL_ITEMS) {
            setWin(true);
            if (curScreen == LevelScreenTypes.ENVY) {
                ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.WIN);
            }else ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        } else if (COMPLETED_LEVEL && !COLLECTED_ALL_ITEMS) {
            setLose(true);
            ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        }
    }

    public void relocateCamera() {
        if (player.getDead()) {
            if (player.recovery==false){
                 player.health--;
             }
            camera.position.x = player.getX() + playerLeftOffset;
            player.setDead(false);
            player.setRecovery(true);
        }
    }
    
    public void updatePeppermint() {
        
    }
    public void updateBoulder() {
	
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
    public abstract LevelScreenTypes getPreviousScreen();
    public abstract LevelScreenTypes getCurrentScreen();
    public abstract LevelScreenTypes getNextScreen();
}