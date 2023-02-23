package com.mygdx.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.objects.Items.Item;
import com.mygdx.objects.Obstacles.Spike;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;
import com.mygdx.screens.LevelScreens.LevelLust;

public class WorldContactListener implements ContactListener {
	@Override
	public void beginContact(Contact contact) {
		Player Actualplayer=GameScreen.player;
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa == null || fb == null) return;
		if(fa.getUserData() == null || fb.getUserData() == null) return;
		System.out.println("A: " + fa.getUserData() + ", B: " + fb.getUserData());
		
		//Check if a Player obj contacted with a spike obj
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Spike || fb.getUserData() instanceof Spike) { 
				Player ffa = (Player) fa.getUserData();
				Actualplayer.damage();
				//Print I am a player, to indicate there is contact
			
			}
		}
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Item || fb.getUserData() instanceof Item) { 
				Player ffa = (Player) fa.getUserData();
				//Actualplayer.damage();
				//Print I am a player, to indicate there is 
				Item ffb = (Item) fb.getUserData();
				ffb.onHit(ffa);
				System.out.println(ffa.getItemsCollected());
		
			
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		//Gdx.app.log("End", "");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}