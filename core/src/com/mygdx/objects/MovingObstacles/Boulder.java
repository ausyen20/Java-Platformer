package com.mygdx.objects.MovingObstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;

public class Boulder extends Enemy{
	private int ID;
	private float originalX, originalY;
	private Vector2 velocity;
	private Body b2dBody;
	private boolean active = false;
	private GameScreen gameScreen;
	
	
	public Boulder(World world, GameScreen gameScreen, MapObject mapObj, int ID) {
		super(world, mapObj);
		this.ID= ID;
		this.b2dBody = body;
		this.gameScreen = gameScreen;
		b2dBody = defineBoulder(mapObj);
		velocity = new Vector2(-1, -3);
		b2dBody.setActive(false);
	
		
	}

	@Override
	public void update(float dt) {
		b2dBody.setLinearVelocity(velocity);
	}

	public void resetBoulder(float playerX, boolean respawn, Boulder b) {
		
	}
	@Override
	public void onHit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Batch batch) {
		// TODO Auto-generated method stub
		
	}
	
	//Create boulder body
	public Body defineBoulder(MapObject mapObj) {
		Ellipse ellipse = ((EllipseMapObject)mapObj).getEllipse();
		float radius = ellipse.width/Constants.PPM;

		originalX = ellipse.x/Constants.PPM ;
		originalY = ellipse.y/Constants.PPM;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		bodyDef.position.set(ellipse.x/Constants.PPM , ellipse.y/Constants.PPM);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		b2dBody = world.createBody(bodyDef);
		shape.setRadius(radius /2);
		shape.setPosition(new Vector2(1, 2.5f));
	//	System.out.println("X: " + original_x + ", y: " + original_y);
		fixDef.shape = shape;
		fixDef.density = 1.0f;
		b2dBody.createFixture(fixDef).setUserData(this);
		shape.dispose();
		
		return b2dBody;
	}
	
	
	public Body getBBody() {
		return this.b2dBody;
	}
	public int getID() {
		return this.ID;
	}
	public float getX() {
		return this.b2dBody.getPosition().x;
	}
	public float getY() {
		return this.b2dBody.getPosition().y;
	}
	public float getOriginalX() {
		return originalX;
	}
	public float getOriginalY() {
		return originalY;
	}
}
