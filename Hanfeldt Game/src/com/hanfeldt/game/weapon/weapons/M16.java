package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.io.Sound;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class M16 extends AmmoWeapon {
	
	private int damage;
	
	/**
	 * She weighs 150 kilograms and fires $200 custom-tooled cartridges at 10,000 rounds per minute.
	 * It cost $400,000 to fire this weapon...for 12 seconds.
	 * From {@link http://en.wikiquote.org/wiki/Team_Fortress_2#Meet_the_Heavy}
	 * 
	 * @param p A {@link Player} entity.
	 */
	public M16(EntityLiving p) {
		super(p, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 2, 1, 1), 30, 180, 30, 10, 65);
		damage = Values.bullet_damage_M16;
		setAutomatic(true);
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			super.addBullet();
			Sound.playSound("weapon/pistol_shoot.ogg");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.ogg");
		}
	}
	
}
