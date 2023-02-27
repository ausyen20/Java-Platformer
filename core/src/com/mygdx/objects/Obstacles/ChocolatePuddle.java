package com.mygdx.objects.Obstacles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;

public class ChocolatePuddle extends InteractiveObstacles{
	private float x, y, width, height;
	private RectangleMapObject rectangleObj;
	public ChocolatePuddle(MapObject mapObject, World world) {
		super(mapObject, world);
		this.rectangleObj = (RectangleMapObject) mapObject;
	
		this.x = rectangleObj.getRectangle().getX() + rectangleObj.getRectangle().getWidth() /2;
		this.y = rectangleObj.getRectangle().getY() + rectangleObj.getRectangle().getHeight() /2;
		this.width = rectangleObj.getRectangle().getWidth();
		this.height = rectangleObj.getRectangle().getHeight();
	
		createPuddle(x,y,width,height);
		
	}

	@Override
	public void onHit(Player player) {
		
		
	}
	
	private void createPuddle(float x, float y, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
		body = world.createBody(bodyDef);
		shape.setAsBox(width/2/Constants.PPM, height/2/Constants.PPM);
		
		fixDef.shape = shape;
		fixDef.friction = 0;
		body.createFixture(fixDef).setUserData(this);
		shape.dispose();
	}

}
