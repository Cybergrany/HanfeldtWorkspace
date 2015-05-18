package com.hanfeldt.game.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.tile.LayerMapTile;
import com.hanfeldt.game.tile.Tile;

/***
 * A map that allows entities to see what layer they should spawn on  by default,
 * and whether layers should be checked on collisions (Bullets, for example)
 * @author ofeldt
 *
 */
//public class LayerMap extends Level{
public class LayerMap{
	
	private final BufferedImage mapImage;
	private int sizeX, sizeY;
	private TileArrayList<LayerMapTile> tiles;
	
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
		
		tiles = new TileArrayList<LayerMapTile>();
		tiles.addTile(sizeX, sizeY, null);
		
		generateLayerMap();
		
	}
	
	private void generateLayerMap(){
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				switch(mapImage.getRGB(x, y)){
					case 0xFF000000://Layer 0 (Black)
						tiles.addTile(x, y, new LayerMapTile(x, y, 0));
						break;
					default://Blank
						tiles.addTile(x, y, new LayerMapTile(x, y, -1));
						break;
				}
			}
		}
	}
	
	public int getDefaultLayer(int x, int y){
		return tiles.getTile(x, y).getLayer();
	}
}
