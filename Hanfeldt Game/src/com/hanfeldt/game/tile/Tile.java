package com.hanfeldt.game.tile;

import com.hanfeldt.game.Sprite;

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
}