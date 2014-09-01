package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class SupportedRoadBase extends Tile{
	
	public SupportedRoadBase(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  3, 6, 1, 1));
	}

	public boolean isSolid(){
		return false;
	}

	public String toString() {
		return "SupportedRoadBase";
	}
}
