package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.npc.Npc;

public class Bill extends Npc {

	public Bill(int x, int y) {
		super(new Sprite(Main.getSpritesheet(), 6, 0, 1, 2), 100, x, y);
	}

}
