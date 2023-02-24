package com.mygdx.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.helpers.Constants;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class Player extends GameEntity{
	
	// Player states
	private enum State {JUMPING, FALLING, WALKING, IDLE}
	private State currState;
	// Player animations
	private Texture playerImage; 
	private TextureRegion[] walkFrames;
	private TextureRegion[] fallFrames;
	private TextureRegion[] upFrames;
	private Animation<TextureRegion> animation;
	private TextureRegion[][] splitFrames;
	private float elapsedtime;
	private Actor actor;
	private int jumpCounter;
	private String blockedKey = "blocked";
	private TiledMapTileLayer collisionLayer;
	private int itemsCollected;

	public boolean DEAD;
	public int health;
	public boolean recovery=false;
	FixtureDef fixDef = new FixtureDef();
	
	//Austin: Remove collisionLayer from constructor, it unnecessary code
	public Player(float width, float height, Body body) {
		super(width, height, body);
		this.speed = 10f;
		this.jumpCounter = 0;
		this.health=3;
		this.itemsCollected = 0; 
		
		// Player animation
		playerImage = new Texture("character/creatureSheet.png");
		splitFrames = TextureRegion.split(playerImage, Constants.PLAYER_SPRITE_WIDTH, Constants.PLAYER_SPRITE_HEIGHT);
		changeState(State.WALKING);
		DEAD = false;
		fixDef.isSensor = true;
	}
		
	public float getLinearVelocity() {
		return body.getLinearVelocity().x;
	}

	public void setLinearVelocity(float velx) {
		body.getLinearVelocity().x = velx;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float newspeed) {
		this.speed = newspeed;
	}
	//Testing function, when a player hits a spike
	public void damage() {
		if (recovery==false) {
		health--;
		setRecovery(true);
		System.out.println("Player: " + health);}
	}
	
	/*
	 * Call animation renders depending on state
	 */
	public void changeState(State state) {
		switch(state) {
			case JUMPING:
				upAnimation();
				currState = State.JUMPING;
				break;
			case FALLING:
				fallAnimation();
				currState = State.FALLING;
				break;
			case WALKING:
				walkAnimation();
				currState = State.WALKING;
				break;
		}
	}

	public void walkAnimation() {
		walkFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkFrames[i] = splitFrames[0][i];
		} 
		animation = new Animation<TextureRegion>(1f/5f, walkFrames);
	}

	public void fallAnimation() {
		fallFrames = new TextureRegion[7];
		for (int i = 0; i < 7; i++) {
			fallFrames[i] = splitFrames[1][i];
		} 
		animation = new Animation<TextureRegion>(1f/7f, fallFrames);
	}

	public void upAnimation() {
		upFrames = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			upFrames[i] = splitFrames[2][i];
		} 
		animation = new Animation<TextureRegion>(1f/6f, upFrames);
	}

	/*
	 * Position the player will start at
	 * Same as the update() method, but it won't be called during rendering, only when initialising
	 * Necessary for the "pause screen at the first 3 seconds" functionality
	 */
	public void initPos() {
		x = body.getPosition().x * Constants.PPM;
		y = body.getPosition().y * Constants.PPM;
	}
	
	@Override
	public void update() {

		x = body.getPosition().x * Constants.PPM;
		y = body.getPosition().y * Constants.PPM;
		
		jump();	
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		elapsedtime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedtime, true), x - width/2, y - height/2, Constants.PLAYER_SPRITE_WIDTH, Constants.PLAYER_SPRITE_HEIGHT);
	}
	
	// Basic Player Movement
	private void jump() {
		float oldX = x, oldY = y;
		boolean collisionX = false, collisionY = false;
		
		velX = (float) 0.15;
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2) {
			changeState(State.JUMPING);
			float force = body.getMass() * 4;
			body.setLinearVelocity(body.getLinearVelocity().x, 0);
			body.applyLinearImpulse(new Vector2(0, force),  body.getPosition(), true);
			jumpCounter++;
		}
		if(body.getLinearVelocity().y < 0 && currState == State.JUMPING) {
			changeState(State.FALLING);
		}
		// reset Jump Counter
		if(body.getLinearVelocity().y == 0) {
			changeState(State.WALKING);
			jumpCounter = 0;
		}
		body.setLinearVelocity(velX * speed, body.getLinearVelocity().y< 25 ? body.getLinearVelocity().y :25);
	}
	
	/*
	
	//If the cell is blocked, then return true
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int)(y/collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
	
	//Collision Detection for different sides
	public boolean collidesRight() {
		boolean collides = false;

		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(getX() + getWidth(), getY() + step))
				break;

		return collides;
	}
	

	public boolean collidesLeft() {
		boolean collides = false;

		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(getX(), getY() + step))
				break;

		return collides;
	}

	public boolean collidesTop() {
		boolean collides = false;

		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(getX() + step, getY() + getHeight()))
				break;

		return collides;
	}

	public boolean collidesBottom() {
		boolean collides = false;

		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(getX() + step, getY()))
				break;

		return collides;
	}
	
	*/
	public void setSpawnPoint() {
		//Set a range, therefore as long the player is still in range. Then set spawn point to regarding flag
		//Lust Layout
			if(body.getPosition().x > 0 && body.getPosition().x < 52f) {
				if(body.getPosition().y < 0 || DEAD) {
					//Reset to player to the start position
					body.setTransform(0.26f, 0.8f, 0);
					setDead(true);
				}
			}else if (body.getPosition().x >= 52f && body.getPosition().x < 106.7f) {
				if(body.getPosition().y < 0 || DEAD) {
					//Checkpoint 1
					body.setTransform(52f, 0.8f, 0);
					setDead(true);
					
				}	
			}else if(body.getPosition().x >= 106.7f && body.getPosition().x < 135.24f) {
				if(body.getPosition().y < 0 || DEAD) {
					//Checkpoint 2
					body.setTransform(107.7f, 0.8f, 0);
					setDead(true);
				}
			}else if (body.getPosition().x >= 135.24f && body.getPosition().x < 158f) {
				if (body.getPosition().y < 0 || DEAD) {
					//Checkpoint 3
					body.setTransform(135.24f, 0.8f, 0);
					setDead(true);
				}
				
			}	
	}

	public boolean getDead() {
		return DEAD;
	}

	public void setDead(boolean state) {
		DEAD = state;
	}
	public void setRecovery(boolean state) {
		recovery=state;
	}
	public void incItemsCollected(){
		this.itemsCollected += 1;
	}
	public int getItemsCollected(){
		return itemsCollected;
	}
	
	
	
}