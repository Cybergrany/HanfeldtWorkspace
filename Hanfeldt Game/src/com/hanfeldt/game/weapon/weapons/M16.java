package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.io.Sound;

public class M16 extends AmmoWeapon {
	
	private int damage;
	
	public M16(Player p) {
		super(p, new Sprite(Main.getSpritesheet(), 3, 4, 1, 1), 30, 60, 30, 10);
		damage = Values.bullet_damage_M16;
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!player.getDirection()) {
				// facing left
				Main.getGame().bullets.add(new Bullet(player.getX() - 5, player.getY() + Main.tileSize -2, damage));
			}else{
				// facing right
				Main.getGame().bullets.add(new Bullet(player.getX() + Main.tileSize + 5, player.getY() + Main.tileSize -2, damage));
			}
			Sound.playSound("weapon/pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.wav");
		}
	}
	
}