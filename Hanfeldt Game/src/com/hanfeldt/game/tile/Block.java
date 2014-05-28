package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class Block extends Tile {
	
	
	public Block(int x, int y) {
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet, 0, 0, 1, 1));
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public String toString() {
		return "Block";
	}
	
}
