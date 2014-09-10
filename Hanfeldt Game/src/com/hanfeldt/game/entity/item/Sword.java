package com.hanfeldt.game.entity.item;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.EntityItem;

public class Sword extends EntityItem{
	
	public Sword(int x, int y) {
		super(new Sprite(Main.getSpritesheet(), 3, 3, 1, 1), x, y);
	}
	
}
