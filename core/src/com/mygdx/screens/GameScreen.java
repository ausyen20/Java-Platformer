package com.mygdx.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.TileMapHelper;
import com.mygdx.objects.player.Player;


public abstract class GameScreen implements Screen {

    // Screen
    protected OrthographicCamera camera;
    protected Viewport viewport;

    // Graphics
    //protected World world;
    protected SpriteBatch batch;
    protected Texture[] backgrounds;
    protected float w = Gdx.graphics.getWidth();
	protected float h = Gdx.graphics.getHeight();

    // Objects
    //protected Player player;
    protected Music music;

    public GameScreen() {
        camera = new OrthographicCamera(16, 9);
        //cameraupdate();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        //world = new World(new Vector2(0,-25f),false);

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
}