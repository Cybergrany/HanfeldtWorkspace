package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class Billy extends NPCCharacter {
	
	public Billy(int x, int y) {
		super(new Sprite(SpriteSheet.getSheet(SpriteSheet.character), 0, 2, 1, 1), 100, x, y);
		setVelXMax(speed);
		setJumpHeight(2.3f);
	}
	
}
