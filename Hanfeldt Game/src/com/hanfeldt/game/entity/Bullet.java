package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;

public class Bullet extends Entity {
	public static final Color COLOR = new Color(0xFF, 0x55, 0x00);
	protected float angle;
	private float speed = 3.0f;
	private static int damageValue = 10;//default damage
	
	public Bullet(int x, int y, int damage) {
		super(x, y);
		setAngle();
		damageValue = damage;
	}
	
	public void tick() {
		setVelX((float) Math.cos(angle*Math.PI/180) * speed);
		setVelY((float) Math.sin(angle*Math.PI/180) * speed);
		changeX(getVelX());
		changeY(getVelY());
		destroyBulletAtBounds();//Lyk dis? --> sure
	}
	
	public void draw(Graphics g) {
		g.setColor(COLOR);
		int posX = getX() - Main.getGame().getPlayer().getX() + (Main.WIDTH /2) - (Main.TILE_SIZE /2);
		g.drawLine(posX, getY(), posX, getY());
	}
	
	private void destroyBulletAtBounds(){
		if (getX() < getX() - (Main.WIDTH /2) ||
			getX() > getX() + (Main.WIDTH /2) ||
			getY() < 0 || getY() > Main.HEIGHT){
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
	
	public int getDamage(){
		return damageValue;
	}
	
	public float getAngle(){
		return angle;
	}
	
	public void setAngle(){
		System.out.println("called");
		try {
			angle = (float) Math.toDegrees(Math.atan2(Main.mouseY - (Main.getGame().getPlayer().getY() + Main.TILE_SIZE),
														Main.mouseX - (Main.getGame().getPlayer().getDirection() ? Main.WIDTH /2 + (Main.TILE_SIZE /2) + 3:Main.WIDTH /2 -3)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
