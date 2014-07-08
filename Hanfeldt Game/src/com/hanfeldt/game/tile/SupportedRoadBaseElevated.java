package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class SupportedRoadBaseElevated extends Tile{
	
	public SupportedRoadBaseElevated(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  4, 6, 1, 1));
	}

	public boolean isSolid(){
		return false;
	}

	public String toString() {
		return "SupportedRoadBaseElevated";
	}
}
