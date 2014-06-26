package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.SpriteSheet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.io.Sound;

public abstract class AmmoWeapon extends TriggerWeapon {
	private int ammoInClip;
	private int totalAmmo;
	private int ammoInFullClip;
	private long reloadStarted;
	private boolean reloadInProg, clipOutDone, clipInDone, reloadClickDone;
	
	/**
	 * An <strong>Ammo-Based Weapon</strong>, such as a gun, which usually shoots bullets or similar projectile.
	 * @param p A {@link Player} entity.
	 * @param s A {@link Sprite} from a valid {@link SpriteSheet}
	 * @param aic The amount of ammo allocated to the weapon's clip when spawned in.
	 * @param ta Total ammo allocated to weapon.
	 * @param aifc Ammo in a full clip
	 * @param tt Amount of time before the trigger can be pressed again.
	 */
	public AmmoWeapon(Player p, Sprite s, int aic, int ta, int aifc, int tt) {
		super(p, s, tt);
		ammoInClip = aic;
		totalAmmo = ta;
		ammoInFullClip = aifc;
	}
	
	public int getAmmoInClip() {
		return ammoInClip;
	}
	
	public void setAmmoInClip(int aic) {
		ammoInClip = aic;
	}
	
	public void changeAmmoInClip(int c) {
		if(Main.debugCheats) {
			return;
		}
		ammoInClip += c;
		if(ammoInClip < 0) {
			ammoInClip = 0;
		}else if(ammoInClip > ammoInFullClip) {
			ammoInClip = ammoInFullClip;
		}
	}
	
	public void changeTotalAmmo(int c) {
		totalAmmo += c;
	}
	
	public int getTotalAmmo() {
		return totalAmmo;
	}
	
	public void setTotalAmmo(int ta) {
		totalAmmo = ta;
	}
	
	public void trigger() {
		changeAmmoInClip(-1);
	}
	
	public void reload() {
		reloadInProg = true;
		reloadStarted = totalTicks;
		clipOutDone = clipInDone = reloadClickDone = false;
	}
	
	public void tick() {
		super.tick();
		if(reloadInProg) {
			if(!clipOutDone) {
				Sound.playSound("weapon/ClipOut.wav");
				clipOutDone = true;
			}
			if(!clipInDone && (totalTicks - reloadStarted) > 30) {
				Sound.playSound("weapon/ClipIn.wav");
				clipInDone = true;
			}
			if(!reloadClickDone && (totalTicks - reloadStarted) > 65) {
				if(totalAmmo > 0) Sound.playSound("weapon/Reload.wav");
				reloadClickDone = true;
				reloadInProg = false;
				
				// Actual reload code
				if(ammoInClip >= ammoInFullClip) {
					return;
				}
				int ammoToReload = ammoInFullClip - ammoInClip;
				if(ammoToReload > totalAmmo) {
					ammoInClip = totalAmmo + ammoInClip;
					totalAmmo = 0;
				}else{
					ammoInClip = ammoInFullClip;
					totalAmmo -= ammoToReload;
				}
			}
			
		}
	}
	
}
