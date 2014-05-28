package com.hanfeldt.game.tile;

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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void onCollidedEntity(Entity e) {}
	
}
