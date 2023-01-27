package com.mygdx.objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.indulge.Indulge;

public abstract class Collectables extends Sprite {
   protected Screen screen;
   protected World world;
   protected Texture texture; 
   protected boolean isAquired;
   protected boolean toAquire;
   protected Body body;
   
   public Collectables(Screen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(), getY(), 16 , 16)
        defineItem();
        toAquire = false;
        isAquired = false;

   }
   public abstract void defineItem();
   public abstract void use();

   /*
    * public void render(float delta){
        checkItemCollision();
        if( !isAquired){
             
        }

        clearScreen();
        draw();
        
   }
    */
   
   public void draw(Batch batch){
        // only draw this item         
        if(!isAquired){
            super.draw(batch);
        }
    }
    
    public void update(float deltaTime){
        
        if(toAquire && !isAquired){
            world.destroyBody(body);
            isAquired = true;
        }
         
       

    }
    public void destroy(){
        isAquired = true;
    }
    /*
     * public void clearScreen(){

   }

   public void checkItemCollision(){
        if (Player.posX == posX && Player.posY == posY && !isAquired){
            isAquired = true;
        }
   }
     */
   

}
