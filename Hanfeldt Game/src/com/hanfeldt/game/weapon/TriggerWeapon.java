package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.SpriteSheet;
import com.hanfeldt.game.entity.Player;

public abstract class TriggerWeapon extends Weapon {
	private int triggerRestTime;
	protected long tickTriggered = 0;
	private boolean triggered = false;
	
	/**
	 * This kind of weapon is simpler than a {@link AmmoWeapon}, requiring no ammo.
	 * @param p A {@link Player} entity.
	 * @param s A {@link Sprite} from a valid {@link SpriteSheet}
	 * @param tt tt Amount of time before the weapon can be triggered again.
	 */
	public TriggerWeapon(Player p, Sprite s, int tt) {
		super(p, s);
		triggerRestTime = tt;
	}
	
	public void tick() {
		super.tick();
		if(totalTicks >= triggerRestTime + tickTriggered) {
			tickTriggered = 0;
			triggered = false;
		}else{
			triggered = true;
		}
	}
	
	public void tryTrigger() {
		if(tickTriggered == 0) {
			tickTriggered = totalTicks;
			trigger();
		}
	}
	
	protected abstract void trigger();
	
	public boolean isTriggered() {
		return triggered;
	}
	
}
