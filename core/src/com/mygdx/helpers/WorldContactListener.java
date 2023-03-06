package com.mygdx.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.objects.Items.Coin;
import com.mygdx.objects.Items.Item;
import com.mygdx.objects.MovingObstacles.Boulder;
import com.mygdx.objects.MovingObstacles.Peppermint;
import com.mygdx.objects.Obstacles.ChocolatePuddle;
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
		//System.out.println("A: " + fa.getUserData() + ", B: " + fb.getUserData());
		
		//Check if a Player obj contacted with a spike obj
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Spike || fb.getUserData() instanceof Spike) { 
				Player ffa = (Player) fa.getUserData();
				//Actualplayer.damage();
			
			}
		}
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Item || fb.getUserData() instanceof Item) { 
				Player ffa = (Player) fa.getUserData();
				//Actualplayer.damage();
				//Print I am a player, to indicate there is 
				Item ffb = (Item) fb.getUserData();
				ffb.onHit(Actualplayer);
				
		
			
			}
		}
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Coin|| fb.getUserData() instanceof Coin) { 
				Player ffa = (Player) fa.getUserData();
				Coin ffb = (Coin) fb.getUserData();
				ffb.onHit(Actualplayer);		
			
			}
		}
		//If player in contact with a choco puddle
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof ChocolatePuddle || fb.getUserData() instanceof ChocolatePuddle) {
				Actualplayer.slowing();
			}
		}
		//If player in contact with peppermint
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Peppermint || fb.getUserData() instanceof Peppermint) {
				Actualplayer.hitByMint();
			}
		}
		//If player in contact with boulder
		if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if(fa.getUserData() instanceof Boulder || fb.getUserData() instanceof Boulder) {
				Actualplayer.hitByMint();
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Player Actualplayer=GameScreen.player;
		Fixture efa = contact.getFixtureA();
		Fixture efb = contact.getFixtureB();
		

		if(efa == null || efb == null) return;
		if(efa.getUserData() == null || efb.getUserData() == null) return;
		
		//System.out.println("eA: " + efa.getUserData() + ", eB: " + efb.getUserData());
		
		if(efa.getUserData() instanceof Player || efb.getUserData() instanceof Player) {
			if(efa.getUserData() instanceof ChocolatePuddle || efb.getUserData() instanceof ChocolatePuddle) {
				Actualplayer.resetSlowing();
				
			}
		}
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