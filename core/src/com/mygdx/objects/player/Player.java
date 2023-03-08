package com.mygdx.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;

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
	private int jumpCounter;
	private int itemsCollected;
	private int coinsCollected;
	public boolean DEAD;
	public boolean SLOW;
	public boolean RESPAWN = false;
	public int health;
	public boolean recovery=false;
	public boolean lowgravity=false;
	FixtureDef fixDef = new FixtureDef();
	
	public Player(float width, float height, Body body) {
		super(width, height, body);
		this.speed = 10f;
		this.jumpCounter = 0;
		this.health=3;
		this.itemsCollected = 0; 
		this.coinsCollected = 0;
		// Player animation
		playerImage = new Texture("character/creatureSheet.png");
		splitFrames = TextureRegion.split(playerImage, Constants.PLAYER_SPRITE_WIDTH, Constants.PLAYER_SPRITE_HEIGHT);
		changeState(State.WALKING);
		DEAD = false;
		fixDef.isSensor = true;
	}
		
	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float newspeed) {
		this.speed = newspeed;
	}

	public void setVelx(float newVelx) {
		this.velX = newVelx;
	}
	
	//Player slowed when in contact with a choco puddle
	public void slowing() {
		if(SLOW == false) {
			SLOW = true;
			this.setVelx(0.05f);
		}
	}
	// Player reset to original velx when leaving choclate puddle
	public void resetSlowing() {
		if(SLOW == true) {
			SLOW = false;
			this.setVelx(0.15f);
		}
	}
	//Player in contact with peppermint, then set it to dead.
	public void hitByMint() {
		this.setDead(true);
		if(this.recovery == false) {
			this.health--;
		}
		this.setDead(false);
		this.setRecovery(true);
	}

	//Testing function, when a player hits a spike
	public void damage() {
		if (recovery==false) {
			((AudioManager) AudioManager.getInstance()).setSound("Sound/Ouch.wav");
        	((AudioManager) AudioManager.getInstance()).playSound();
			health--;
			setRecovery(true);
		}
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
		if(!SLOW) {
			velX = (float) 0.15;
		}
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
		if((body.getLinearVelocity().y == 0 || body.getLinearVelocity().y == -1.26217745E-29f) && currState == State.FALLING) {
			changeState(State.WALKING);
			jumpCounter = 0;
		}
		body.setLinearVelocity(velX * speed, body.getLinearVelocity().y< 25 ? body.getLinearVelocity().y :25);
	}

	public void setSpawnsLust() {
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

	//Spawnpoints for Level Gluttony
	public void setSpawnsGluttony() {
		if(body.getPosition().x > 0 && body.getPosition().x < 38.3f) {
			if(body.getPosition().y < 0 || DEAD) {
				//Reset to player to the start position
				body.setTransform(1.78f, 0.83f, 0);
				setDead(true);
				setRespawn(true);
			}
		}else if (body.getPosition().x >= 38.3f && body.getPosition().x < 65.53f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(38f, 3.4f, 0);
				setDead(true);
				setRespawn(true);
			}
		}else if (body.getPosition().x >= 65.53f && body.getPosition().x < 95f) {
			if(body.getPosition().y < 0 || DEAD) {
				//Checkpoint between torch and chocolate pud
				body.setTransform(65.53f, 1.3275f, 0);
				setDead(true);
				setRespawn(true);
			}
		}else if (body.getPosition().x >= 95f && body.getPosition().x < 130.8f) {
			if(body.getPosition().y < 0 || DEAD) {
				//CheckPoint next to candy cane 
				body.setTransform(95f, 1.3275f, 0);
				setDead(true);
				setRespawn(true);
				
			}
		}else if (body.getPosition().x >= 130.8f && body.getPosition().x < 158.26f) {
			if(body.getPosition().y < 0 || DEAD) {
				//Before icecream item and endpoint
				body.setTransform(130.8f, 3.84f, 0);
				setDead(true);
				setRespawn(true);
			}
		}
	}

	//Spawn points for Sloth Level
	public void setSpawnsSloth() {
		setRespawn(false);
		if(body.getPosition().x > 0 && body.getPosition().x < 48.9f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(1.78f, 0.83f, 0);
				setDead(true);
			}
		}else if (body.getPosition().x >= 49f && body.getPosition().x < 101.2f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(49f, 1f, 0);
				setDead(true);
			}
		}else if (body.getPosition().x >= 101.2f && body.getPosition().x < 155.8f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(101.2f, 4.33f, 0);
				setDead(true);
			}
		}
	}
	//Spawns for greed
	public void setSpawnsGreed() {
		if(body.getPosition().x > 0 && body.getPosition().x < 42.5f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(0.33f, 0.83f, 0);
				setDead(true);
			}
		}else if (body.getPosition().x >= 42.5f && body.getPosition().x < 109.5f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(44f, 4.83f, 0);
				setDead(true);
			}
		}else if (body.getPosition().x >= 109.5f && body.getPosition().x < 158f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(109.5f, 1.33f, 0);
				setDead(true);
			}
		}
	}
	//Spawns for Envy
	public void setSpawnsEnvy() {
		if(body.getPosition().x > 0 && body.getPosition().x < 77f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(1.73f, 0.83f, 0);
				setDead(true);
				setRespawn(true);
			}
		}else if (body.getPosition().x >= 77f && body.getPosition().x < 117f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(77.2f, 1.8388f, 0);
				setDead(true);
				setRespawn(true);
			}
		}else if (body.getPosition().x >= 117f && body.getPosition().x < 158f) {
			if(body.getPosition().y < 0 || DEAD) {
				body.setTransform(118.5f, 3.33f, 0);
				setDead(true);
				setRespawn(true);
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
	public boolean getRespawn() {
		return RESPAWN;
	}
	public void setRespawn(boolean flag) {
		this.RESPAWN = flag;
	}
	public void incItemsCollected(){
		this.itemsCollected += 1;
	}
	public int getItemsCollected(){
		return itemsCollected;
	}
	public void incCoinsCollected(){
		this.coinsCollected += 1;
	}
	public void resetCoinsCollected(){
		this.coinsCollected = 0;
	}
	public int getCoinsCollected(){
		return coinsCollected;
	}
	public float getPositionX() {
		return body.getPosition().x;
	}
	public float getPositionY() {
		return body.getPosition().y;
	}
	public float getPPM_X() {
		return body.getPosition().x * Constants.PPM;
	}
	public float getPPM_Y() {
		return body.getPosition().y * Constants.PPM;
	}
	
	
	
}