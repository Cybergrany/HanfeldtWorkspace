package com.hanfeldt.game.entity.projectile;

import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class GrenadeRPG extends BulletLobbed{

	public GrenadeRPG(int x, int y) {
		super(new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 4, 1, 1), x, y, Values.bullet_damage_RPG);
	}

}
