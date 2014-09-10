package com.hanfeldt.game.tile;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class RoofLamp extends Tile{
	public RoofLamp(int x, int y){
		super(x, y);
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block), 3, 0, 1, 1));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString() {
		return "RoofLamp";
	}
}
