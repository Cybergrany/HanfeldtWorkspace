package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;

public class Tile {
	private Sprite sprite;
	private int x, y;
	
	public boolean isSolid = false, isVisible = false;
	public String name;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile(int x, int y, int sx, int sy, int w, int h){
		this.x = x;
		this.y = y;
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block), sx, sy, w, h));
	}
	
	public boolean isSolid() {
		return isSolid;
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
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public void onCollidedEntity(Entity e) {}
	
}
