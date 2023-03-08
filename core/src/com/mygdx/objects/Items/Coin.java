package com.mygdx.objects.Items;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;
import com.mygdx.screens.LevelScreens.LevelGreed;

public class Coin extends Collectables {
	private Texture itemTexture;
	private Boolean isCollected;
	private Boolean toBeCollected;
	private float width;
	private float height;
	private float x;
	private float y;
	public Coin(GameScreen gameScreen, float x, float y, float width, float height) {
		super(gameScreen, x, y);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.isCollected = false;
		this.toBeCollected = true;
		this.itemTexture = new Texture("Items/Coin.png");
		create();
	}

	@Override
	public void create() {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
		body = world.createBody(bodyDef);
		shape.setAsBox(width/2/Constants.PPM, height/2/Constants.PPM);
		fixDef.shape = shape;
		fixDef.isSensor = true;
		body.createFixture(fixDef);
		body.createFixture(fixDef).setUserData(this);
		shape.dispose();
		
		
	}

	@Override
	public void render(SpriteBatch batch) {
		if(!isCollected) {
			batch.draw(itemTexture,x - width /2 , y - height /2 , 12, 12);
		}
		
	}
	public Body getBody() {
		return body;
	}
	
	public void onHit(Player player) {
		isCollected = true;
		itemTexture.dispose();
		if(toBeCollected){
			player.incCoinsCollected();		
			toBeCollected = false;
			((AudioManager) AudioManager.getInstance()).setSound("Sound/Coin3.wav");
        	((AudioManager) AudioManager.getInstance()).playSound();
			if(player.getCoinsCollected() % 30 == 0 && gameScreen.getClass()==LevelGreed.class ){
				player.incItemsCollected(); 
				((AudioManager) AudioManager.getInstance()).setSound("Sound/Collectable4.wav");
        		((AudioManager) AudioManager.getInstance()).playSound();     
			}
			
			if(player.getCoinsCollected() % 20== 0 && gameScreen.getClass()!=LevelGreed.class){
				if(player.health <6){
					player.health++;
					player.resetCoinsCollected();
					((AudioManager) AudioManager.getInstance()).setSound("Sound/Coin1.wav");
        			((AudioManager) AudioManager.getInstance()).playSound();
        
				}
				
			}

		}

			
	}


}
	


