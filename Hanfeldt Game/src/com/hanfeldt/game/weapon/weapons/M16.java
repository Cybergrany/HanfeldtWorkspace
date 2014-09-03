package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.io.Sound;

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
		super(p, new Sprite(Main.getSpritesheet(), 3, 4, 1, 1), 30, 180, 30, 10);
		damage = Values.bullet_damage_M16;
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!entity.getDirection()) {
				// facing left
				Main.getGame().bullets.add(new Bullet(entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage));
			}else{
				// facing right
				Main.getGame().bullets.add(new Bullet(entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage));
			}
			Sound.playSound("weapon/pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.wav");
		}
	}
	
}
