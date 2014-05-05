package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;

public abstract class TriggerWeapon extends Weapon {
	private int triggerRestTime;
	private long tickTriggered = 0;
	
	public TriggerWeapon(Player p, Sprite s, int tt) {
		super(p, s);
		triggerRestTime = tt;
	}
	
	public void tick() {
		super.tick();
		if(totalTicks >= triggerRestTime + tickTriggered) {
			tickTriggered = 0;
		}
	}
	
	public void tryTrigger() {
		if(tickTriggered == 0) {
			tickTriggered = totalTicks;
			trigger();
		}
	}
	
	protected abstract void trigger();
	
}
