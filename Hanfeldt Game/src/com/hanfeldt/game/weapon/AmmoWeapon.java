package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;

public abstract class AmmoWeapon extends TriggerWeapon {
	private static int ammoInClip;
	private int totalAmmo;
	private int ammoInFullClip;
	public AmmoWeapon(Player p, Sprite s, int aic, int ta, int aifc, int tt) {
		super(p, s, tt);
		ammoInClip = aic;
		totalAmmo = ta;
		ammoInFullClip = aifc;
	}
	
	public static int getAmmoInClip() {
		return ammoInClip;
	}
	
	public void setAmmoInClip(int aic) {
		ammoInClip = aic;
	}
	
	public void changeAmmoInClip(int c) {
		ammoInClip += c;
		if(ammoInClip < 0) {
			ammoInClip = 0;
		}else if(ammoInClip > ammoInFullClip) {
			ammoInClip = ammoInFullClip;
		}
	}
	
	public int getTotalAmmo() {
		return totalAmmo;
	}
	
	public void setTotalAmmo(int ta) {
		totalAmmo = ta;
	}
	
}
