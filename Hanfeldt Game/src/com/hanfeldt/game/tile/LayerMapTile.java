package com.hanfeldt.game.tile;

public class LayerMapTile extends Tile{
	
	private int layer;

	public LayerMapTile(int x , int y, int layer){
		super(x, y);
		this.layer = layer;
	}
	
	public int getLayer(){
		return layer;
	}
	
}
