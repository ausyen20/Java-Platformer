package com.mygdx.objects.Obstacles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;

public abstract class InteractiveObstacles {
	protected World world;
	protected Body body;
	protected MapObject mapObject;
	//For Interactive objs
	public InteractiveObstacles(MapObject mapObject, World world) {
		this.mapObject = mapObject;

		this.world = world;
	}
	
	
	public abstract void onHit(Player player);
	
	public World getWorld() {
		return world;
	}
	
	public MapObject getMapObj() {
		return mapObject;
	}
}

