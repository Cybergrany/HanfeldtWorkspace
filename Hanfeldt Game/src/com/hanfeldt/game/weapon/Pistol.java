package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.io.Sound;

public class Pistol extends AmmoWeapon {
	
	private int ammo;
	
	public Pistol(Player p, int aic, int ta, int aifc, int tt) {
		super(p, new Sprite(Main.getSpritesheet(), 0, 4, 1, 1), aic, ta, aifc, tt);
		ammo = getAmmoInClip();
	}
	
	public void trigger() {
		if(!player.getDirection()) {
			// facing left
			Main.getGame().bullets.add(new Bullet((Main.sizeX /2) - (Main.tileSize /2) -3, player.getY() + Main.tileSize));
		}else{
			// facing right
			Main.getGame().bullets.add(new Bullet((Main.sizeX /2) + (Main.tileSize /2) +4, player.getY() + Main.tileSize));
		}
		changeAmmoInClip(ammo-1);
		Sound.playSound("pistol_shoot.wav");
		System.out.println(getAmmoInClip());
	}
	
}
