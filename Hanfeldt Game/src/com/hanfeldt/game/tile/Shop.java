package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class Shop extends Tile{
	
	public Shop(int x, int y){
		super(x, y);
		setSprite((new Sprite(Main.spriteSheet, 0, 7, 2, 1)));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString(){
		return "Shop";
	}

}
