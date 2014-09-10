package com.hanfeldt.game.tile;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class RoadBase extends Tile{
	
	public RoadBase(int x, int y){
		super(x, y);
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block),  3, 2, 1, 1));
	}

	public boolean isSolid(){
		return false;
	}

	public String toString() {
		return "SupportedRoadBase";
	}
}
