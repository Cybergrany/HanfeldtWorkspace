package com.hanfeldt.game.tile;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class CementBack extends Tile{
	
	public CementBack(int x, int y){
		super(x, y);
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block),  0,  0, 1, 1));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString() {
		return "CementBack";
	}

}
