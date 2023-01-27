package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Constants;

public abstract class GameScreen implements Screen {

    // Screen
    protected OrthographicCamera camera;
    protected Viewport viewport;

    // Graphics
    protected SpriteBatch batch;
    protected Texture[] backgrounds;
    protected World world;
    protected float w = Gdx.graphics.getWidth();
	protected float h = Gdx.graphics.getHeight();

    public GameScreen() {
        camera = new OrthographicCamera(16, 9);
        cameraupdate();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        world=new World(new Vector2(0,0),false);
    }

    private void cameraupdate() {
    	Vector3 position=camera.position;
    	position.x=0;
    	position.y=0;
    	position.z=0;
     	camera.position.set(new Vector3(position));
    	camera.setToOrtho(false, position.x, position.y);
    	camera.update();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public abstract void render(float delta);   
    

    @Override
    public void resize(int width, int height) {
        camera.position.set(width/2f, height/2f, 0);
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}
    
}