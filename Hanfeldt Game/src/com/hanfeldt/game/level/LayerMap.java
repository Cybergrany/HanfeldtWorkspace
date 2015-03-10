package com.hanfeldt.game.level;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.tile.Tile;

/***
 * A map that allows entities to see what layer they should spawn on  by default,
 * and whether layers should be checked on collisions (Bullets, for example)
 * @author ofeldt
 *
 */
public class LayerMap extends Level{
	
	private final BufferedImage mapImage;
	
	public LayerMap(String path){
		BufferedImage temp = null;
		
		try{
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e){
			System.err.println("Unable to load layermap, stacktrace;");
			e.printStackTrace();
		}finally{
			mapImage = temp;
		}
		
		sizeX= mapImage.getWidth();
		sizeY = mapImage.getHeight();
		
		tiles = new TileArrayList<Tile>();
		tiles.addTile(sizeX, sizeY, null);
		
		generateLayerMap();
		
	}
	
	public void generateLayerMap(){
		
	}
}
