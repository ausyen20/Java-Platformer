package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		//System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());
		if(fa.getUserData() == null || fb.getUserData() == null) return;
	
		
		System.out.println("A: " + fa.getUserData() + ", B: " + fb.getUserData());
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Gdx.app.log("End", "");
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
