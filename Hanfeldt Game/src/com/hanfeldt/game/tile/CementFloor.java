package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class CementFloor extends Tile{
	public CementFloor(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  1, 5, 1, 1));
	}
	
	public boolean isSolid(){
		return true;
	}
	
	public String toString() {
		return "CementFloor";
	}
}
