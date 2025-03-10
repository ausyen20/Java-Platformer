package com.mygdx.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.objects.Items.Coin;
import com.mygdx.objects.Items.Item;
import com.mygdx.objects.MovingObstacles.Boulder;
import com.mygdx.objects.MovingObstacles.Enemy;
import com.mygdx.objects.MovingObstacles.Peppermint;
import com.mygdx.objects.Obstacles.ChocolatePuddle;
import com.mygdx.objects.Obstacles.Spike;
import com.mygdx.objects.player.Player;
import com.mygdx.screens.LevelScreens.GameScreen;
import com.mygdx.screens.LevelScreens.LevelEnvy;
import com.mygdx.screens.LevelScreens.LevelGluttony;
import com.mygdx.screens.LevelScreens.LevelGreed;
import com.mygdx.screens.LevelScreens.LevelLust;
import com.mygdx.screens.LevelScreens.LevelSloth;

import static com.mygdx.helpers.Constants.PPM;


public class TileMapHelper {
	private TiledMap tiledMap;
	private GameScreen gameScreen;
	protected Fixture fixture;

	private Array<Peppermint> peppermints;
	private Array<Boulder> boulders;
	private Array<Boulder> boulders1;
	
	public TileMapHelper(GameScreen level) {
		this.gameScreen = level;
	}

	public OrthogonalTiledMapRenderer setupMap() {
		if (gameScreen.getClass()==LevelLust.class) {
			tiledMap = new TmxMapLoader().load("layouts/LustLayout.tmx");}
		if (gameScreen.getClass()==LevelSloth.class) {
			tiledMap = new TmxMapLoader().load("layouts/Sloth.tmx");
		}
		if (gameScreen.getClass()==LevelGluttony.class) {
			tiledMap = new TmxMapLoader().load("layouts/GluttonyLayout.tmx");
		}
		if (gameScreen.getClass()==LevelGreed.class) {
			tiledMap = new TmxMapLoader().load("layouts/Greed.tmx");
		}
		if (gameScreen.getClass()==LevelEnvy.class) {
			tiledMap = new TmxMapLoader().load("layouts/Envy.tmx");
		}
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
		FixtureDef fixDef = new FixtureDef();
		
		String polygonName = polygonmapObject.getName();
		Shape shape = createPolygonShape(polygonmapObject);
		
		fixDef.shape = shape;
		fixDef.density = 1000;
		fixDef.friction = 0;
		body.createFixture(fixDef);
		body.createFixture(fixDef).setUserData(polygonName);
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
						
			if(mapObject instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
				String rectangleName = mapObject.getName();
				if(rectangleName.equals("coin")){
					gameScreen.setCoin(new Coin(gameScreen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight(),
					rectangle.getWidth(), rectangle.getHeight()));
				}
				if(gameScreen.getClass()==LevelLust.class){
					setLevelItems("LustItems", rectangle, rectangleName);
				}					
				if (gameScreen.getClass()==LevelGluttony.class) {
					setLevelItems("GluttonyItems", rectangle, rectangleName);
				}
				if (gameScreen.getClass()==LevelSloth.class) {
					setLevelItems("SlothItems", rectangle, rectangleName);
				}
				if (gameScreen.getClass()==LevelEnvy.class) {
					setLevelItems("EnvyItems", rectangle, rectangleName);
				}
				
			}
		}
	}

	private void setLevelItems(String itemFolderName, Rectangle rectangle, String rectangleName){
		
		if(rectangleName.equals("item0")) {
			gameScreen.setItem0(new Item( "Items/" + itemFolderName + "/item0.png", gameScreen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight(),
			rectangle.getWidth(), rectangle.getHeight()));
		
		}
		if(rectangleName.equals("item1")) {
			
			gameScreen.setItem1(new Item( "Items/" + itemFolderName + "/item1.png", gameScreen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight(),
			rectangle.getWidth(), rectangle.getHeight()));
		
		}
		if(rectangleName.equals("item2")) {
			
			gameScreen.setItem2(new Item( "Items/" + itemFolderName + "/item2.png", gameScreen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight(),
			rectangle.getWidth(), rectangle.getHeight()));
		
		}

	}
	
	//Creating obstacles
	private void parseObstacles(MapObjects mapObjects) {
		peppermints = new Array<Peppermint>();
		boulders = new Array<Boulder>();
		boulders1 = new Array<Boulder>();
		int counter = 1;
		int bcounter = 1;
		int b1counter = 1;
		
		for (MapObject mapObject : mapObjects) {
			//creating spikes
			if (mapObject instanceof PolygonMapObject) {				
				String polyName = mapObject.getName();
	
				if(polyName.equals("spike")) {
					new Spike(((PolygonMapObject) mapObject), gameScreen.getWorld());
				}
			}
			//Creating choco puddles
			if(mapObject instanceof RectangleMapObject) {
				String rectName = mapObject.getName();
				
				if(rectName.equals("puddle")) {
					new ChocolatePuddle(((RectangleMapObject) mapObject), gameScreen.getWorld());
				}
			}
			//Creating peppermints and boulders
			if(mapObject instanceof EllipseMapObject) {
				String ellipseName = mapObject.getName();
				Ellipse ellipse = ((EllipseMapObject)mapObject).getEllipse();
		
				if(ellipseName.equals("peppermint")) {					
					peppermints.add(new Peppermint(gameScreen.getWorld(),gameScreen, mapObject, counter));
					counter++;
				}
				if(ellipseName.equals("boulder")) {
					boulders.add(new Boulder(gameScreen.getWorld(), gameScreen, mapObject, bcounter, (int)ellipse.width, (int)ellipse.height));
					bcounter++;
				}
				if(ellipseName.equals("boulder1")) {
					boulders1.add(new Boulder(gameScreen.getWorld(), gameScreen, mapObject, b1counter,(int)ellipse.width, (int)ellipse.height));
					b1counter++;
				}	
			}
		}
	}

	public Array<Peppermint> getPeppermint(){
		return peppermints;
	}
	public Array<Boulder> getBoudler(){
		return boulders;
	}
	public Array<Boulder> getBoulder1(){
		return boulders1;
	}
	
	public Array<Enemy> getEnemies(){
		Array<Enemy> enemies = new Array<Enemy>();
		enemies.addAll(peppermints);
		enemies.addAll(boulders);
		return enemies;
	}

	
}