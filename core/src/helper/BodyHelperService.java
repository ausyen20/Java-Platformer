package helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static helper.Constants.PPM;
public class BodyHelperService {

	public static Body creatBody(float x, float y, float width, float height, boolean isStatic, World world) {
		BodyDef bodydef = new BodyDef();
		bodydef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
		bodydef.position.set(x/PPM, y/PPM);
		bodydef.fixedRotation = true;
		Body body = world.createBody(bodydef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2/PPM, height/2/PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		//fixtureDef.friction = 0;
		body.createFixture(fixtureDef);
		body.createFixture(fixtureDef).setUserData("player");
		
		shape.dispose();
		return body;
	}
}
