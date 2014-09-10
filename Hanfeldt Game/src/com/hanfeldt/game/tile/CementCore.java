package com.hanfeldt.game.tile;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class CementCore extends Tile{
	public CementCore(int x, int y){
		super(x, y);
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block),  0, 0, 1, 1));
	}
	
	public boolean isSolid(){
		return true;
	}
	
	public String toString() {
		return "CementCore";
	}
}
