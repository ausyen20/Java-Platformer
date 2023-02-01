package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.indulge.GameScreen;

import objects.player.Player;

import static helper.Constants.PPM;

public class tileMapHelper {

	private TiledMap tiledMap;
	//TL
	private TiledMapTileLayer collisionLayer;
	protected Fixture fixture;
	
	private GameScreen gameScreen;

	
	
	public tileMapHelper(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
	}
	
	public OrthogonalTiledMapRenderer setupMap() {
		//Load the whole map 
		tiledMap = new TmxMapLoader().load("sources/maps/map0.tmx");
		//in Tiled, objects layer
		
		parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
		return new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	private void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject : mapObjects) {
		
		if(mapObject instanceof PolygonMapObject) {
				
				
				createStaticBody((PolygonMapObject) mapObject);
				
				String poly = mapObject.getName();
				if(poly.equals("spike")) {
					
				}
				
			}
			
			
			if(mapObject instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
				String rectangleName = mapObject.getName();
				
				if(rectangleName.equals("player")) {
					Body body = BodyHelperService.creatBody(
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
	//create polygon objects for character to stand on
	private void createStaticBody (PolygonMapObject polygonMapObject) {
		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = gameScreen.getWorld().createBody(bodyDef);
		String name = polygonMapObject.getName();
		
		
	
		Shape shape = createPolygonShape(polygonMapObject);
		body.createFixture(shape, 1000);
		body.createFixture(shape, 1000).setUserData(name);
		shape.dispose();
	}

	private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
		float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length/2];
		
		for(int i = 0;  i < vertices.length/2 ; i++) {
				Vector2 current = new Vector2(vertices[i*2] / PPM, vertices[i*2 + 1] /PPM);
				worldVertices[i] = current;
		}
		
		PolygonShape shape = new PolygonShape();
		shape.set(worldVertices);
		return shape;
	}
	

}
