package helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class tileMapHelper {

	private TiledMap tiledMap;
	
	public tileMapHelper() {
		
	}
	
	public OrthogonalTiledMapRenderer setupMap() {
		//Load the whole map 
		tiledMap = new TmxMapLoader().load("sources/maps/map0.tmx");
		
		return new OrthogonalTiledMapRenderer(tiledMap);
		
	}
	
}
