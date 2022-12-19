package com.mygdx.indulge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.Constants;
import helper.tileMapHelper;

public class GameScreen extends ScreenAdapter{
	//creating basic step ups for a whole new world
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	private static final float PPM = Constants.PPM;
	
	//This relating to creating a level 
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private tileMapHelper tileMapHelper;
	
	public GameScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0,0), false);
		this.box2DDebugRenderer = new Box2DDebugRenderer();
		
		//Creating map (level)
		this.tileMapHelper = new tileMapHelper();
		this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
		
	}
	
	private void update() {
		world.step(1/60f,6, 2);
		cameraUpdate();
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
			
		}
	}
	
	private void cameraUpdate() {
		//Set the camera's position (original pos: 0,0,0)
		camera.position.set(new Vector3(800, 800, 0));
		camera.update();
		
	}
	
	@Override
	public void render(float delta) {
		this.update();
		//This changes the color on the background
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Map need to be render before batch starts
		orthogonalTiledMapRenderer.render();
		
		batch.begin();
		//render objects
		
		batch.end();
		box2DDebugRenderer.render(world, camera.combined.scl(PPM));
	}
	
}
