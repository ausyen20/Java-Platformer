package com.mygdx.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.objects.player.Player;

public class BodyHelper {
   
	
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Constants.PPM, height/2/Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef);
         //Should add a line find the player triangle
        body.createFixture(fixtureDef).setUserData("player");
        shape.dispose();
        return body;
    }

    //Create Player body, fix to Dynamic body, everything remains the same from createbody
    public static Body createPlayer(float x, float y, float width, float height, World world) {
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.fixedRotation = true;
    	bodyDef.type = BodyDef.BodyType.DynamicBody;
    	bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
    	
    	PolygonShape shape = new PolygonShape();
    	shape.setAsBox(width/2/Constants.PPM, height/2/Constants.PPM);
    	
    	FixtureDef fixDef = new FixtureDef();
    	fixDef.shape = shape;
    	Body body = world.createBody(bodyDef);
    	body.createFixture(fixDef);
    	//Setting Player body to Player class
    	Player player = new Player(width, height, body);
    	body.createFixture(fixDef).setUserData(player);
    	shape.dispose();
    	return body;
    }
    
}