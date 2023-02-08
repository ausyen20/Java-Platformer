package com.mygdx.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelLust;
import static com.mygdx.helpers.Constants.PPM;

public class TileMapHelper {
	private TiledMap tiledMap;
	private LevelLust gameScreen;

	private TiledMapTileLayer collisionLayer;
	protected Fixture fixture;

	public TileMapHelper(LevelLust lust) {
		this.gameScreen = lust;
	}

	public OrthogonalTiledMapRenderer setupMap() {
		tiledMap = new TmxMapLoader().load("layouts/LustLayout.tmx");
		parseMapObjects(tiledMap.getLayers().get("Object Layer 1").getObjects());
		return new OrthogonalTiledMapRenderer(tiledMap);
	}

	private void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject:mapObjects) {
			if(mapObject instanceof PolygonMapObject) {
				createStaticBody((PolygonMapObject) mapObject);
				// testing to find the object through their name
				/*String poly = mapObject.getName();
				if(poly.equals("spike")) {
				}*/
			}

			if(mapObject instanceof RectangleMapObject ){
				Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
				String rectangleName = mapObject.getName();

				if(rectangleName.equals("player")) {
					Body body = BodyHelper.createBody(
					rectangle.getX() + rectangle.getWidth() / 2, 
					rectangle.getY() + rectangle.getHeight() / 2,
					rectangle.getWidth(),
					rectangle.getHeight(), 
					false, 
					gameScreen.getWorld()
					);
					gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body, collisionLayer));
				}
			}
		}
	}
	
	private void createStaticBody(PolygonMapObject polygonmapObject) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = gameScreen.getWorld().createBody(bodyDef);

		// layout branch code
		/*Shape shape = createPolygonShape(polygonmapObject);
		body.createFixture(shape, 1000);
		shape.dispose();*/
		
		String polygonName = polygonmapObject.getName();
		Shape shape = createPolygonShape(polygonmapObject);
		body.createFixture(shape, 1000);
		//Fetching and naming each polygon object with their given name
		body.createFixture(shape, 1000).setUserData(polygonName);
		shape.dispose();
	}
	private Shape createPolygonShape(PolygonMapObject polygonmapObject) {
		float[]vertices=polygonmapObject.getPolygon().getTransformedVertices();
		Vector2[] worldvertices = new Vector2[vertices.length/2];

		for(int i = 0; i < vertices.length/2; i++) {
			Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2+1]/PPM);
			worldvertices[i] = current;
		}
		PolygonShape shape= new PolygonShape();
		shape.set(worldvertices);
		return shape;
	}
}