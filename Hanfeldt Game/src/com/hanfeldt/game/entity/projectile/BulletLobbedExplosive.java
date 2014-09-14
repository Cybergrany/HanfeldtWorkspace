package com.hanfeldt.game.entity.projectile;

import com.hanfeldt.game.display.Sprite;

public class BulletLobbedExplosive extends BulletLobbed{
	int explosive;

	public BulletLobbedExplosive(Sprite s, int x, int y, int damage, int explosive) {
		super(s, x, y, damage);
		this.explosive = explosive;
	}

}
