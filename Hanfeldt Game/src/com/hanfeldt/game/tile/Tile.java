package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Entity;

public class Tile {
	private Sprite sprite;
	private int x, y;
	private boolean tileFilled = false;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		tileFilled = true;
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public int getTileX() {
		return x;
	}
	
	public int getTileY() {
		return y;
	}
	
	public int getX() {
		return x *Main.TILE_SIZE;
	}
	
	public int getY() {
		return y *Main.TILE_SIZE;
	}
	
	public boolean tileFilled(){
		return tileFilled;
	}
	
	public void onCollidedEntity(Entity e) {}
	
}
