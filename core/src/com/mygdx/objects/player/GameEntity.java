package com.mygdx.objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity {

	protected float x, y, velX, velY, speed;
	protected float width, height;
	protected Body body;
	
	public GameEntity(float width, float height, Body body) {
		this.x = body.getPosition().x;
		this.y = body.getPosition().y;
		this.width = width;
		this.height = height;
		this.body = body;
		this.velX = 0;
		this.velY = 0;
		this.speed = 0;
		
	}
	
	public abstract void update();
	
	public abstract void render(SpriteBatch batch);
	//Body Getter
	public Body getBody() {
		return body;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	

}