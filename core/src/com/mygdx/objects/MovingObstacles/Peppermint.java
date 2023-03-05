package com.mygdx.objects.MovingObstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
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

public class Peppermint extends Enemy{
	private int ID;
	private float original_x;
	private float original_y;
	private Vector2 velocity;
	public CircleMapObject circleMapObj;
	private Body b2dBody;
	private GameScreen gameScreen;
	private boolean active = false;
	private float elapsedtime;
	private Texture peppermintImage;
	private TextureRegion[] frames;
	private Animation<TextureRegion> animation;
	private TextureRegion[][] splitFrames;
	
	public Peppermint(World world, GameScreen gameScreen, MapObject mapObject, int ID) {
		super(world, mapObject);
		this.ID = ID;
		this.b2dBody = body;
		this.gameScreen = gameScreen;
		b2dBody = defineObstacle(mapObject);
		velocity = new Vector2(-1, -2);
		b2dBody.setActive(false);
		peppermintImage = new Texture("obstacles/peppermint.png");
		splitFrames = TextureRegion.split(peppermintImage, 20, 20);
		frames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			frames[i] = splitFrames[0][i];
		} 
		animation = new Animation<TextureRegion>(1f/8f, frames);
	
	}

	@Override
	public void update(float dt) {
		b2dBody.setLinearVelocity(velocity);
	}
	
	//Use to reset Mints
	public void resetPeppermint(float playerX, boolean respawn, Peppermint p) {
		//System.out.println("pID: " + p.getID() + ", pX: " + p.getOriginalX() + ", pY: " + p.getOriginalY());
	
		if(p.getID() == 1) {
				p.b2dBody.setActive(false);
				p.b2dBody.setTransform(p.getOriginalX(), p.getOriginalY(), 0);
		}
		
		if(p.getID() == 2) {
			p.b2dBody.setActive(false);
			p.b2dBody.setTransform(p.getOriginalX(), p.getOriginalY(), 0);
		}
		
	}
	
	@Override
	public void onHit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Batch batch) {
		elapsedtime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedtime, true), b2dBody.getWorldCenter().x * Constants.PPM - 10, b2dBody.getWorldCenter().y * Constants.PPM - 10);
	}
	
	//Help parsing the Ellipse Object (in-Complete)
	protected Body defineObstacle(MapObject mapObj) {
		Ellipse ellipse = ((EllipseMapObject)mapObj).getEllipse();
		float radius = ellipse.width/Constants.PPM;

		original_x = ellipse.x/Constants.PPM ;
		original_y = ellipse.y/Constants.PPM;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		bodyDef.position.set(ellipse.x/Constants.PPM , ellipse.y/Constants.PPM);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		b2dBody = world.createBody(bodyDef);
		shape.setRadius(radius /2);
		shape.setPosition(new Vector2(1, 2.5f));
		System.out.println("X: " + original_x + ", y: " + original_y);
		fixDef.shape = shape;
		fixDef.density = 1.0f;
		b2dBody.createFixture(fixDef).setUserData(this);
		shape.dispose();
		
		return b2dBody;
	}
	
	public float getX() {
		return this.b2dBody.getPosition().x;
	}
	public float getY() {
		return this.b2dBody.getPosition().y;
	}
	public Body getMintBody() {
		return this.b2dBody;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActivng(boolean active) {
		this.active = active;
	}
	public float getOriginalX() {
		return original_x;
	}
	public float getOriginalY() {
		return original_y;
	}
	public int getID() {
		return ID;
	}
}
