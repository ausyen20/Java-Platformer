package com.mygdx.objects.Items;

import javax.xml.transform.Templates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.helpers.Constants;
import com.mygdx.screens.LevelScreens.GameScreen;

public class Item extends Collectibles {
	private Texture itemTexture;
	private Boolean isCollected;
	private float width;
	private float height;
	private float x;
	private float y;
	public Item(String assetPath,GameScreen gameScreen, float x, float y, float width, float height) {
		super(gameScreen, x, y);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.isCollected = false;
		this.itemTexture = new Texture(assetPath);
		createItem();
	}

	@Override
	public void createItem() {
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
			batch.draw(itemTexture,x - width /2 , y - height /2 , 16, 16);
		}
		
	}
	public Body getBody() {
		return body;
	}
	
	public void onHit() {
		isCollected = true;
		itemTexture.dispose();
			
	}


	
	

}
