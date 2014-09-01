package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class CementBack extends Tile{
	
	public CementBack(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  0, 5, 1, 1));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString() {
		return "CementBack";
	}

}
