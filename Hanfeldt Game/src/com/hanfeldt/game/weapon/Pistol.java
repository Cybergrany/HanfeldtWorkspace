package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.io.Sound;

public class Pistol extends AmmoWeapon {
	
	public Pistol(Player p, int aic, int ta, int aifc, int tt) {
		super(p, new Sprite(Main.getSpritesheet(), 0, 4, 1, 1), aic, ta, aifc, tt);
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!player.getDirection()) {
				// facing left
				Main.getGame().bullets.add(new Bullet((Main.sizeX /2) - (Main.tileSize /2) -3, player.getY() + Main.tileSize));
			}else{
				// facing right
				Main.getGame().bullets.add(new Bullet((Main.sizeX /2) + (Main.tileSize /2) +4, player.getY() + Main.tileSize));
			}
			Sound.playSound("pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("pistol_empty.wav");
		}
	}	
}
