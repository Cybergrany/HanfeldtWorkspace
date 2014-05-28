package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.npc.Npc;

public class Bullet extends Entity {
	private float angle;
	private static final Color color = new Color(0xFF, 0x55, 0x00);
	private long totalTicks = 0;
	private float speed = 3.0f;
	
	public Bullet(int x, int y) {
		super(x, y);
		try {
			angle = (float) Math.toDegrees(Math.atan2(Main.mouseY - (Main.getGame().getPlayer().getY() + Main.tileSize),
														Main.mouseX - (Main.getGame().getPlayer().getDirection() ? Main.sizeX /2 + (Main.tileSize /2) + 3:Main.sizeX /2 -3)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		totalTicks++;
		if(totalTicks >= Long.MAX_VALUE) {
			totalTicks = 0;
		}
		setVelX((float) Math.cos(angle*Math.PI/180) * speed);
		setVelY((float) Math.sin(angle*Math.PI/180) * speed);
		changeX(getVelX());
		changeY(getVelY());
		destroyBulletAtBounds();//Lyk dis? --> sure
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		int posX = getX() - Main.getGame().getPlayer().getX() + (Main.sizeX /2) - (Main.tileSize /2);
		g.drawLine(posX, getY(), posX, getY());
	}
	
	private void destroyBulletAtBounds(){
		if (getX() < getX() - (Main.sizeX /2) ||
			getX() > getX() + (Main.sizeX /2) ||
			getY() < 0 || getY() > Main.sizeY){
			destroyBullet();
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
