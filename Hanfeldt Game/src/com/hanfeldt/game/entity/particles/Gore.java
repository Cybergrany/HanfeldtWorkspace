package com.hanfeldt.game.entity.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;

public class Gore extends Entity {
	private EntityLiving entity;

	public Gore(int x, int y, EntityLiving e) {
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (rand.nextBoolean() ? 1 : -1);
		velY = rand.nextFloat() *-1.5f;
		entity = e;
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(Graphics g) {
//		int screenX = getX()
//				    - (Main.getGame().getPlayer().getX() - Main.WIDTH /2 - Main.TILE_SIZE /2);
		int screenX = getX() - entity.getX() + Main.TILE_SIZE + Main.WIDTH / 2 + entity.getSizeX();
		int screenY = getY() - entity.getY() - Main.TILE_SIZE + Main.HEIGHT / 2 + entity.getSizeY();
		if(screenX > 0 && screenX < Main.WIDTH){
			g.setColor(Color.RED);
			g.fillRect(screenX, getY(), 1, 1);
		}
	}
	
}
