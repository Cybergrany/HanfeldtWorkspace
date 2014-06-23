package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.io.Sound;

public class Pistol extends AmmoWeapon {
	
	public Pistol(Player p, int aic, int ta) {
		super(p, new Sprite(Main.getSpritesheet(), 0, 4, 1, 1), aic, ta, 8, 10);
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!player.getDirection()) {
				// facing left
				Main.getGame().bullets.add(new Bullet(player.getX() - 3, player.getY() + Main.tileSize));
			}else{
				// facing right
				Main.getGame().bullets.add(new Bullet(player.getX() + Main.tileSize + 3, player.getY() + Main.tileSize));
			}
			Sound.playSound("weapon/pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.wav");
		}
	}	
}
