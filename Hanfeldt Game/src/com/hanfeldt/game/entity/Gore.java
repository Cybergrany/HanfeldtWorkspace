package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.hanfeldt.game.Main;

public class Gore extends Entity {

	public Gore(int x, int y) {
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (rand.nextBoolean() ? 1 : -1);
		velY = rand.nextFloat() *-1.5f;
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(Graphics g) {
		int screenX = getX()
				    - (Main.getGame().getPlayer().getX() - Main.WIDTH /2 - Main.tileSize /2);
		if(screenX > 0 && screenX < Main.WIDTH){
			g.setColor(Color.RED);
			g.fillRect(screenX, getY(), 1, 1);
		}
	}
	
}
