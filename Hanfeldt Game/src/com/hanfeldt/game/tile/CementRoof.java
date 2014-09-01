package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class CementRoof extends Tile{
	public CementRoof(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  0, 6, 1, 1));
	}
	
	public boolean isSolid(){
		return true;
	}
	
	public String toString() {
		return "CementRoof";
	}
}
