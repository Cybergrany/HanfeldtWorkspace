package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class CementCoreModern extends Tile{
	public CementCoreModern(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  4, 5, 1, 1));
	}
	
	public boolean isSolid(){
		return true;
	}
	
	public String toString() {
		return "CementCoreModern";
	}
}
