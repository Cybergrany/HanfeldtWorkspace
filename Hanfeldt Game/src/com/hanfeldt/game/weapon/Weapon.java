package com.hanfeldt.game.weapon;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.entity.Player;

public abstract class Weapon {
	private Sprite sprite;
	protected Player player;
	protected long totalTicks = 0;
	
	public Weapon(Player p, Sprite s) {
		player = p;
		sprite = s;
	}
	
	public void tick() {
		totalTicks++;
		if(totalTicks == Long.MAX_VALUE) {
			totalTicks = Long.MAX_VALUE;
		}
	}
	
	public void draw(Graphics g) {
		if(!player.getDirection()) {
			sprite.draw(g, (Main.sizeX /2) - Main.tileSize, player.getY() + (Main.tileSize /2), player.getDirection());
		}else{
			sprite.draw(g, (Main.sizeX /2), player.getY() + (Main.tileSize /2), player.getDirection());
		}
	}
	
}
