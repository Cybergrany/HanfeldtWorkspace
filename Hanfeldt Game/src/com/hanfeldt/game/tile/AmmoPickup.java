package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class AmmoPickup extends Tile{

	public AmmoPickup(int x, int y) {
		super(x, y);
		setSprite(new Sprite(Main.spriteSheet, 2, 5, 1, 1));
	}

}
