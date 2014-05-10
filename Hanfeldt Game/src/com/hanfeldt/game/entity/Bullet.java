package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.npc.Npc;

public class Bullet extends Entity {
	private float angle;
	private static final Color color = new Color(0xFF, 0x55, 0x00);
	private long totalTicks = 0;
	
	public Bullet(int x, int y) {
		super(x, y);
		try {
			angle = (float) -(Math.atan2(Main.mouseX - x, Main.mouseY - y)) + 190.08f;
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
		if (getX() < getX() - (Main.sizeX /2) ||
			getX() > getX() + (Main.sizeX /2) ||
			getY() < 0 || getY() > Main.sizeY){
			Main.getGame().bullets.remove(this);
		}
	}
	
	public void destroyBullet(){
		Main.getGame().bullets.remove(this);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), getSizeX(), getSizeY());
	}
	
	public int getSizeX() {
		return 1;
	}
	
	public int getSizeY() {
		return 1;
	}
	
}
