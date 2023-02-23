package com.mygdx.objects.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.screens.LevelScreens.GameScreen;

public abstract class Collectibles extends Sprite{

	protected GameScreen gameScreen;
	protected World world;
	protected Body body;
	protected Boolean destoryed;
	

	public Collectibles(GameScreen gameScreen, float x, float y) {
		this.gameScreen = gameScreen;
		this.world = gameScreen.getWorld();
		destoryed = false;		
		
	}
	
	public abstract void createItem();

	public abstract void render(SpriteBatch batch);
	
	public void destory() {
		destoryed = true;
	}
	
}
