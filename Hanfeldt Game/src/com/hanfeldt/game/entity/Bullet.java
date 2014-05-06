package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class Bullet extends Entity {
	private float angle;
	private static final Color color = new Color(216, 177, 96);
	private long totalTicks = 0;
	
	public Bullet(int x, int y) {
		super(x, y);
		try {
			angle = (float) -(Math.atan2(Main.mouseX - x, Main.mouseY - y)) + 190.02f;
		}catch(Exception e) {
			angle = 0;
		}
	}
	
	public void tick() {
		totalTicks++;
		if(totalTicks >= Long.MAX_VALUE) {
			totalTicks = 0;
		}
		changeX((float) Math.cos(angle));
		changeY((float) Math.sin(angle));
		// TODO If it goes out of bounds, destroy it
		destroyBulletAtBounds();//Lyk dis?
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(getX(), getY(), getX(), getY());
	}
	
	private void destroyBulletAtBounds(){
		if(getX() < -30 || getX() > Main.sizeX + 30 || getY() < - 30 || getY() > Main.sizeY + 30){
			Main.getGame().bullets.remove(this);
		}
	}
	
}
