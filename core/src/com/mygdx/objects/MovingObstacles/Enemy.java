package com.mygdx.objects.MovingObstacles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;

public abstract class Enemy {
	protected World world;
	protected GameScreen gameScreen;
	protected MapObject mapObj;
	protected Body body;
	
	//Might be in complete
	public Enemy(World world, MapObject mapObj) {
		this.world = world;
		this.mapObj = mapObj;
		//defineObstacle(mapObj);

	
	}
	public abstract void update(float dt);
	public abstract void onHit(Player player);
	public abstract void render(Batch batch);
	//protected abstract void defineObstacle(MapObject mapObject);
	

}
