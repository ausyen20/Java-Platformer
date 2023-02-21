package com.mygdx.objects.Obstacles;

import static com.mygdx.helpers.Constants.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.objects.player.Player;

public class Spike extends InteractiveObstacles {

	public Spike(MapObject mapObject, World world) {
		super(mapObject, world);
		createSpike((PolygonMapObject)mapObject);
	}

	@Override
	public void onHit(Player player) {
		player.health--;
		player.setRecovery(true);
		
	}
	//Create Spike body
	private void createSpike(PolygonMapObject polygonMapObject) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		
		float[]vertices= polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldvertices = new Vector2[vertices.length/2];

		for(int i = 0; i < vertices.length/2; i++) {
			Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2+1]/PPM);
			worldvertices[i] = current;
			
		}
		shape.set(worldvertices);
		fixDef.shape = shape;
		fixDef.isSensor = true;
		body.createFixture(fixDef);
		body.createFixture(fixDef).setUserData(this);
		
		shape.dispose();
		
		
	}

}
