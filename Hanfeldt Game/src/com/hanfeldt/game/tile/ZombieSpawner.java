package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class ZombieSpawner extends Tile{
	
	public ZombieSpawner(int x, int y){
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet,  0, 3, 1, 1));
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString() {
		return "ZombieSpawner";
	}
	
}
