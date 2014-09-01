package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class Billy extends Npc {
	
	public Billy(int x, int y) {
		super(new Sprite(Main.getSpritesheet(), 6, 2, 1, 1), 100, x, y);
	}
	
}
