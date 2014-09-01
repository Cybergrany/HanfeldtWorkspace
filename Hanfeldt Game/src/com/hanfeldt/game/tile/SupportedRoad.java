package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class SupportedRoad extends Tile{
	public SupportedRoad(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  3, 5, 1, 1));
	}
	
	public boolean isSolid(){
		return true;
	}
	
	public String toString() {
		return "SupportedRoad";
	}
}