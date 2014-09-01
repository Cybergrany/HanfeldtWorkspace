package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class Air extends Tile {
	
	public Air(int x, int y) {
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet, 0, 3, 1, 1));
	}
	
	public String toString() {
		return "Air";
	}
	
}
