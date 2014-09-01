package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class RoofLamp extends Tile{
	public RoofLamp(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet, 1, 6, 1, 1));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString() {
		return "RoofLamp";
	}
}
