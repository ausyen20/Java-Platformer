package com.mygdx.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.helpers.Constants;

public class Player extends GameEntity{

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM; 
        y = body.getPosition().y * Constants.PPM; 

        
    }

    @Override
    public void render(SpriteBatch batch) {
        
    }
    
}
