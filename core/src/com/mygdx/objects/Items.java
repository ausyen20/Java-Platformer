package com.mygdx.objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Items extends Sprite{
    protected Screen screen;
    protected World world;
    protected Texture texture; 
    protected Body body;

    public Items(Screen screen, Texture texture, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        this.texture = texture;
      
        setPosition(x, y);
        defineItem();
        

    }
    public abstract void defineItem();
    public abstract void use();
    public abstract void draw(Batch batch);
    public abstract void update(float time);

    
}
