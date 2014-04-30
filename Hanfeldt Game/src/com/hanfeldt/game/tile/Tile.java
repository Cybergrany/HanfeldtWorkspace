package com.hanfeldt.game.tile;

import com.hanfeldt.game.Sprite;

public class Tile {
	private Sprite sprite;
	private int x, y;
	
	public int id = -1;
	
	public static final int air = 0;
	public static final int block = 1;
	
	public Tile(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
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
