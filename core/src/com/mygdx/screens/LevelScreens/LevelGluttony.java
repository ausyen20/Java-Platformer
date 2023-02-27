package com.mygdx.screens.LevelScreens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.WorldContactListener;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.indulge.Indulge;
import com.mygdx.objects.MovingObstacles.Peppermint;
import com.mygdx.objects.player.Player;

public class LevelGluttony extends GameScreen {
	
    // Timing
    private float[] backgroundOffsets = {0, 0, 0};
    //private float bgMaxScrollingSpeed;
    private Texture[] backgrounds;

    // Timing
    private float cameraScrollingSpeed;
    private float constantScrollingSpeed;
    private float playerLeftOffset;
    private float playerSpeed;
    private Vector2 linearVel;
    
    // Tiled Map
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    protected Box2DDebugRenderer box2DDebugRenderer;
    private boolean active;

    Label coinCount;
    Stage coinStage;

    public LevelGluttony() {
        // Add background assets

    	this.batch = new SpriteBatch();
        this.front_batch = new SpriteBatch();
        this.textbatch = new SpriteBatch();

        this.world = new World(new Vector2(0,-7f),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        world.setContactListener(new WorldContactListener());
        player.initPos();
        constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
        cameraScrollingSpeed = constantScrollingSpeed;
        cameraUpdate();
        playerLeftOffset = (camera.position.x * 100) - player.getX();
        playerSpeed = player.getSpeed();
        linearVel = new Vector2(0,0);

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/gluttony00.png");
        backgrounds[1] = new Texture("backgrounds/gluttony01.png");
        backgrounds[2] = new Texture("backgrounds/gluttony02.png");
        // Set background scrolling speed
        batch = new SpriteBatch();
        
     // Set music
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/Clown.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();

        coinStage = new Stage(new ScreenViewport(camera),front_batch);
        coinCount = new Label(String.format("%d", player.getCoinsCollected()), new Label.LabelStyle(font24,Color.WHITE));
        coinStage.addActor(coinCount);
    }


    @Override
    public void render(float deltaTime) {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        super.pauseScreen();
        
        if (player.health<=0) {
            setLose(true);
        	((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        }
    	world.step(1/60f,6, 2);
        // if paused, set deltatime to 0 to stop background scrolling
        if (PAUSED || FIRSTPAUSED) {
            deltaTime = 0;
        } 
        
        camera.update(true);
        this.update();
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.SLOTH);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !FIRSTPAUSED) {
            if (PAUSED) {
                player.getBody().setLinearVelocity(linearVel);
            } else {
                linearVel = player.getBody().getLinearVelocity();
            }
            PAUSED = !PAUSED;
        }

        batch.setProjectionMatrix(this.camera.combined);
        front_batch.setProjectionMatrix(this.camera.combined);
        // batch for the background
        batch.begin();
        renderBackground(deltaTime);
        batch.end();
        orthogonalTiledMapRenderer.render();

        // batch for foreground (player, etc)
        front_batch.begin();
        front_batch.setColor(1,1,1,1f);
        if (player.recovery==true) {
            front_batch.setColor(1,0,0,1f);
            if(!PAUSED) {
                recoverycooldown++;
                if (recoverycooldown>60) {
                    player.setRecovery(false);
                    recoverycooldown=0;
                }
            }
        }
        getItem0().render(front_batch);
        getItem1().render(front_batch);
        getItem2().render(front_batch);
        player.render(front_batch);
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
        }
        front_batch.end();
        coinCount.setText(String.format("%02d",player.getCoinsCollected()));
        coinCount.setPosition(camera.position.x+ 130, 160);
        coinStage.draw();
        // Show back to menu button if game paused
        if (PAUSED) { 
            super.drawButtons(); 
        } else if (!LOSE_LEVEL && !WIN_LEVEL) {
            Gdx.input.setInputProcessor(null);
        } 


        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));}

    private void renderBackground(float deltaTime) {
    
        backgroundOffsets[0] += deltaTime * getScrollingSpeed() / 6;
        backgroundOffsets[1] += deltaTime * getScrollingSpeed() / 4; 
        backgroundOffsets[2] += deltaTime * getScrollingSpeed() / 2; 

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > Constants.ASSET_BACKGROUND_WIDTH) {
                backgroundOffsets[layer] = 0;
            }
            for (int i = 0; i < 2; i++) {
                batch.draw(backgrounds[layer],
                        -1 * backgroundOffsets[layer] + camera.position.x - camera.viewportWidth / 2 + (i * Constants.ASSET_BACKGROUND_WIDTH), 
                        0,
                        Constants.ASSET_BACKGROUND_WIDTH,
                        Constants.WORLD_HEIGHT);
            }
        }
    }
    
    private void update() {
		world.step(1/60f,6, 2);
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);	
        // If game is not paused, update camera and player
        if (!PAUSED && !FIRSTPAUSED) {
            cameraUpdate();
            player.update();
            acceleratePlayer();
            outOfScreenLeft();
            winCondition();
            player.setSpawnsGluttony();
            relocateCamera();
            world.setGravity(new Vector2(0, -7f));
            // Getting peppermint array from tile map helper 
            for(Peppermint p: tileMapHelper.getPeppermint()) {
            	p.update(1f);
            //System.out.println("B: " + p.getActive());
           
            	//When player's x position is beyond a certain range, active the body
             	// + (value)f , when value is smaller lands faster, larger then lands later
            	if(p.getX() < player.getPositionX() + 250f / Constants.PPM) {
            		//Set true to be active when in range
            		p.getMintBody().setActive(true);
            		p.setActivng(true);
            	
            	}
            	
           // System.out.println("X: " + player.getPositionX() + ", " + p.getOriginalX()+ ", P: " + p.getX() + ", ID: " + p.getID() + ", respawn: " + player.getRespawn() + ", active: " + p.getActive());
            	
           //System.out.println("X: " + player.getPositionX() + " -> " + p.getID() + " , " + p.getX() +" , " + p.getY());
            	// If id = 1, first pepper mint
            	if(p.getID() == 1) {
            		if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
            			
            			if(player.RESPAWN == true) {
            				System.out.println("reset1");
            				
            				p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
            				player.setRespawn(false);
            			
            			}
            		}
            	}
            	//If id =2, second pepper mint
            	if(p.getID() == 2) {
            		if(player.getPositionX() > 95f && player.getPositionX() < 131.8f) {
            			if(player.RESPAWN == true) {
            				System.out.println("reset2");
            				
            				p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
            				player.setRespawn(false);
            			
            			}
            		}
            	}
            }
        } else {
            player.getBody().setLinearVelocity(0, 0);
            world.setGravity(new Vector2(0, 0));
        }
	}

    private void acceleratePlayer() {
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
            ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        } else if (COMPLETED_LEVEL && !COLLECTED_ALL_ITEMS) {
            setLose(true);
            ((Indulge) Indulge.getInstance()).change_menu(MenuScreenTypes.END);
        }
    }

    private void relocateCamera() {
        if (player.getDead()) {
        	if (player.recovery==false){
             	player.health--;
             	System.out.println(player.health);
             }
            camera.position.x = player.getX() + playerLeftOffset;
            player.setDead(false);
            player.setRecovery(true);
        }
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
		// Camera center to the player obj
		//position.x = Math.round((player.getBody().getPosition().x  * Constants.PPM * 10) / 10f) + Constants.WORLD_WIDTH / 3;
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

    @Override
    public void show() {
        super.show();
        restartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Indulge) Indulge.getInstance()).change_levels(LevelScreenTypes.GLUTTONY);

            }
        });
    }

   
        
    
    public Player getPlayer() {
    	return player;
    }


    @Override
    public LevelScreenTypes getCurrentScreen() {
        return LevelScreenTypes.GLUTTONY;
    }


    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.SLOTH;
    }
}
