package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Pistol;

@Deprecated
public class AmmoPickup extends Tile{
	private boolean hasAmmo = true;
	
	public AmmoPickup(int x, int y) {
		super(x, y);
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block), 0, 1, 1, 1));
	}
	
	public void onCollidedEntity(Entity e) {
//		super.onCollidedEntity(e);
		if(hasAmmo && e instanceof Player) {
			// This is temporary while I work out why tile.setTile() doesn't work... TODO fix that
//			((Player)e).setWeaponEquipped(new Pistol((EntityLiving)e));
			Weapon wep = Main.getGame().getPlayer().getWeaponEquipped();
			if(wep instanceof AmmoWeapon) {
				AmmoWeapon ammoWep = (AmmoWeapon) wep;
				ammoWep.setTotalAmmo(ammoWep.getTotalAmmo() + Values.bullets_per_crate);
				hasAmmo = false;
			}
		}
	}
	
	public String toString() {
		return "Ammo";
	}
	
}
