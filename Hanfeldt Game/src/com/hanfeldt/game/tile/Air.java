package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class Air extends Tile {
	
	private static int id = air;
	
	public Air(int x, int y) {
		super(x, y, id);
		setSprite(new Sprite(Main.spriteSheet, 0, 3, 1, 1));
	}
	
}
