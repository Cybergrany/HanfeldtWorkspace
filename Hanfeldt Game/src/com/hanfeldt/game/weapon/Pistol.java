package com.hanfeldt.game.weapon;

import com.hanfeldt.game.Bullet;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.Player;
import com.hanfeldt.game.Sprite;

public class Pistol extends AmmoWeapon {
	
	public Pistol(Player p, int aic, int ta, int aifc, int tt) {
		super(p, new Sprite(Main.getSpritesheet(), 0, 4, 1, 1), aic, ta, aifc, tt);
	}
	
	public void trigger() {
		if(!player.getDirection()) {
			Main.getGame().bullets.add(new Bullet((Main.sizeX /2) - Main.tileSize -8, player.getY() + (Main.tileSize /2)));
		}else{
			Main.getGame().bullets.add(new Bullet((Main.sizeX /2) +8, player.getY() + (Main.tileSize /2)));
		}
	}
	
}
