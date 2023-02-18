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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.objects.Obstacles.Spike;
import com.mygdx.objects.player.Player;

import com.mygdx.screens.LevelScreens.LevelLust;

import static com.mygdx.helpers.Constants.PPM;

public class TileMapHelper {
	private TiledMap tiledMap;
	private LevelLust gameScreen;
	protected Fixture fixture;
	private Player player;
	
	
	public TileMapHelper(LevelLust lust) {
		this.gameScreen = lust;
	}

	public OrthogonalTiledMapRenderer setupMap() {
		tiledMap = new TmxMapLoader().load("layouts/LustLayout.tmx");
		parseMapObjects(tiledMap.getLayers().get("Object Layer 1").getObjects());
		parseObjs(tiledMap.getLayers().get("Items").getObjects());
		parseObstacles(tiledMap.getLayers().get("Obstacles Object").getObjects());
		return new OrthogonalTiledMapRenderer(tiledMap);
	}
	

	private void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject:mapObjects) {
			if(mapObject instanceof PolygonMapObject) {
				
				
				createStaticBody((PolygonMapObject) mapObject);
			}

			if(mapObject instanceof RectangleMapObject ){
				Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
				String rectangleName = mapObject.getName();

				if(rectangleName.equals("player")) {
					//Austin: changed from createbody to (new) createplayer method
					Body body = BodyHelper.createPlayer(
					rectangle.getX() + rectangle.getWidth() / 2, 
					rectangle.getY() + rectangle.getHeight() / 2,
					rectangle.getWidth(),
					rectangle.getHeight(), 
					gameScreen.getWorld()
					); 
					
					gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));					
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

	private void parseObjs(MapObjects mapObjects) {
		for(MapObject mapObject : mapObjects) {
			
			if(mapObject instanceof PolygonMapObject) {
				createSensorObj((PolygonMapObject) mapObject);
			}
		}
	}
	
	private void createSensorObj(PolygonMapObject polygonMapObject) {
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.StaticBody;
		Body body2 = gameScreen.getWorld().createBody(bodyDef2);
		FixtureDef fixtureDef2 = new FixtureDef();
		//Duplicate of createPolygonshape
		float[]vertices= polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldvertices = new Vector2[vertices.length/2];

		for(int i = 0; i < vertices.length/2; i++) {
			Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2+1]/PPM);
			worldvertices[i] = current;
			
		}
		
		PolygonShape shape2 = new PolygonShape();
		shape2.set(worldvertices);
		//
		fixtureDef2.shape = shape2;
		fixtureDef2.isSensor = true;
		body2.createFixture(fixtureDef2);
		body2.createFixture(fixtureDef2).setUserData(polygonMapObject.getName());
	
		shape2.dispose();
		
	}
	
	//Creating only spikes, from Spike class
	private void parseObstacles(MapObjects mapObjects) {
		for (MapObject mapObject : mapObjects) {
			if (mapObject instanceof PolygonMapObject) {
				
				String polyName = mapObject.getName();
				
				if(polyName.equals("spike")) {
					new Spike(((PolygonMapObject) mapObject), gameScreen.getWorld());
					
				}
			}
		}
	}

	
}