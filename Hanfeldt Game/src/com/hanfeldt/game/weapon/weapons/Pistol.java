package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.io.Sound;

public class Pistol extends AmmoWeapon {
	private int damage;
	
	/**
	 * Pew-Pew-Pew
	 * 
	 * @param p A {@link Player} Sprite
	 * @param aic 
	 * @param ta
	 */
	public Pistol(Player p) {
		super(p, new Sprite(Main.getSpritesheet(), 0, 4, 1, 1), 8, 64, 8, 10);
		damage = Values.bullet_damage_pistol;
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!player.getDirection()) {
				// facing left
				Main.getGame().bullets.add(new Bullet(player.getX() - 5, player.getY() + Main.TILE_SIZE -2, damage));
			}else{
				// facing right
				Main.getGame().bullets.add(new Bullet(player.getX() + Main.TILE_SIZE + 5, player.getY() + Main.TILE_SIZE -2, damage));
			}
			Sound.playSound("weapon/pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.wav");
		}
	}	
}
