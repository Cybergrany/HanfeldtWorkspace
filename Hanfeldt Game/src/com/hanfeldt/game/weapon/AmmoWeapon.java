package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.io.Sound;

public abstract class AmmoWeapon extends TriggerWeapon {
	public int damage;
	private int ammoInClip;
	private int totalAmmo;
	private int ammoInFullClip;
	private int reloadTime = 65;
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
	 * @param rt Time it takes to reload in Middle Earth time units. i.e: nobody knows how long this actually is.
	 */
	public AmmoWeapon(EntityLiving p, Sprite s, int aic, int ta, int aifc, int tt, int rt) {
		super(p, s, tt);
		ammoInClip = aic;
		totalAmmo = ta;
		ammoInFullClip = aifc;
		reloadTime = rt;
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
	
	public void addBullet(){
		if(!entity.getDirection()) {
			// facing left
//			Main.getGame().getLayers().get(entity.getLayer()).addBullet(new Bullet(entity, entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
			Main.getGame().getEntityManager().addBullet(new Bullet(entity, entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
		}else{
			// facing right
//			Main.getGame().getLayers().get(entity.getLayer()).addBullet(new Bullet(entity, entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
			Main.getGame().getEntityManager().addBullet(new Bullet(entity, entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
		}
	}
	
	public void tick() {
		super.tick();
		if(reloadInProg) {
			if(!clipOutDone) {
				Sound.playSound("weapon/ClipOut.ogg");
				clipOutDone = true;
			}
			if(!clipInDone && (totalTicks - reloadStarted) > 30) {
				Sound.playSound("weapon/ClipIn.ogg");
				clipInDone = true;
			}
			if(!reloadClickDone && (totalTicks - reloadStarted) > reloadTime) {
				if(totalAmmo > 0) Sound.playSound("weapon/Reload.ogg");
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
