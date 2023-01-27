package com.mygdx.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.screens.LevelLust;
import com.mygdx.helpers.Constants;

public class TileMapHelper {
	private static final float PPM = 32;
	private TiledMap tiledMap;
	private LevelLust gameScreen;
	public TileMapHelper(LevelLust lust) {
		this.gameScreen=lust;
	}
	public OrthogonalTiledMapRenderer setupMap() {
		tiledMap= new TmxMapLoader().load("layouts/LustLayout.tmx");
		parseMapObjects(tiledMap.getLayers().get("Object Layer 1").getObjects());
		return new OrthogonalTiledMapRenderer(tiledMap,5);
	}
	private void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject:mapObjects) {
			if(mapObject instanceof PolygonMapObject) {
				createStaticBody((PolygonMapObject) mapObject);
			}
		}
	}
	
	private void createStaticBody(PolygonMapObject polygonmapObject) {
		BodyDef bodyDef= new BodyDef();
		bodyDef.type=BodyDef.BodyType.StaticBody;
		Body Body=gameScreen.getWorld().createBody(bodyDef);
		Shape shape=createPolygonShape(polygonmapObject);
		Body.createFixture(shape, 1000);
		shape.dispose();
	}
	private Shape createPolygonShape(PolygonMapObject polygonmapObject) {
		float[]vertices=polygonmapObject.getPolygon().getTransformedVertices();
		Vector2[] worldvertices=new Vector2[vertices.length/2];
		for(int i=0;i<vertices.length/2;i++) {
			Vector2 current = new Vector2(vertices[i*2]/PPM,vertices[i*2+1]/PPM);
			worldvertices[i]=current;
		}
		PolygonShape shape= new PolygonShape();
		shape.set(worldvertices);
		return shape;
	}
}
