package com.mygdx.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.helpers.TileMapHelper;

//import helper.tileMapHelper;

import com.mygdx.helpers.Constants;

public class Player extends GameEntity{
	
	private int jumpCounter;
	private String blockedKey = "blocked";
	private TiledMapTileLayer collisionLayer;
	private TileMapHelper TMH;
	
	
	public Player(float width, float height, Body body, TiledMapTileLayer collisionLayer) {
		super(width, height, body);
		this.speed = 10f;
		this.jumpCounter = 0;
		this.collisionLayer = collisionLayer;
	}
	
	@Override
	public void update() {
		x = body.getPosition().x * Constants.PPM;
		y = body.getPosition().y * Constants.PPM;
		
		System.out.println("X: " + body.getPosition().x + ", Y: " + body.getPosition().y );
		checkUserInput();	
		OutofBound();
	}

	@Override
	public void render(SpriteBatch batch) {
		
	}
	
	//Basic Player Movement *note: vertical movement is fixed in project
	//This is just a testing 
	private void checkUserInput() {
		float oldX = x, oldY = y;
		boolean collisionX = false, collisionY = false;
		
		velX = (float) 0.15;
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2) {
			float force = body.getMass() * 10;
			body.setLinearVelocity(body.getLinearVelocity().x, 0);
			body.applyLinearImpulse(new Vector2(0, force),  body.getPosition(), true);
			jumpCounter++;
		}
		//reset Jump Counter
		if(body.getLinearVelocity().y == 0) {
			jumpCounter = 0;
		}
		body.setLinearVelocity(velX * speed, body.getLinearVelocity().y< 25 ? body.getLinearVelocity().y :25);
		
	}
	
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
	
	
	public void OutofBound() {
		if(body.getPosition().y < 0) {
			System.out.println("out");
		}
		
	}
}