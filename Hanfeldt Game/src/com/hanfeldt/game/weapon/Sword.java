package com.hanfeldt.game.weapon;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;

public class Sword extends TriggerWeapon {
	
	public Sword(Player p) {
		super(p, new Sprite(Main.getSpritesheet(), 3, 3, 1, 1), 40);
	}

	protected void trigger() {
		
	}
	
	public void draw(Graphics g) {
		if(!player.getDirection()) {
			sprite.draw(g, (Main.sizeX /2) - Main.tileSize -2, player.getY() + (Main.tileSize /2), player.getDirection());
		}else{
			sprite.draw(g, (Main.sizeX /2) +2, player.getY() + (Main.tileSize /2), player.getDirection());
		}
	}
	
}
