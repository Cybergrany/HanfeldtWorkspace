package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Entity;

public class Tile {
	private Sprite sprite;
	private int x, y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public void onCollidedEntity(Entity e) {}
	
}
