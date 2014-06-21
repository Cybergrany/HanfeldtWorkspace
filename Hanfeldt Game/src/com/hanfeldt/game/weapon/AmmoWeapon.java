package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.io.Sound;

public abstract class AmmoWeapon extends TriggerWeapon {
	private int ammoInClip;
	private int totalAmmo;
	private int ammoInFullClip;
	private long reloadStarted;
	private boolean reloadInProg, clipOutDone, clipInDone, reloadClickDone;
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
				}else{
					ammoInClip = ammoInFullClip;
					totalAmmo -= ammoToReload;
				}
			}
			
		}
	}
	
}
