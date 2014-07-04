	package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;

public abstract class Weapon {
	protected Sprite sprite;
	protected Player player;
	protected long totalTicks = 0;
	
	public Weapon(Player p, Sprite s) {
		player = p;
		sprite = s;
	}
	
	public void tick() {
		totalTicks++;
		if(totalTicks == Long.MAX_VALUE) {
			totalTicks = Long.MAX_VALUE;
		}
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
