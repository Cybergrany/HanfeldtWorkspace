package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class Bullet extends Entity {
	private float angle;
	private static final int length = 2;
	private static final Color color = new Color(216, 177, 96);
	private long totalTicks = 0;
	
	public Bullet(int x, int y) {
		super(x, y);
		try {
			angle = (float) Math.toDegrees(Math.atan((y -Main.mouseY)/(x -Main.mouseX)));
		}catch(Exception e) {
			angle = 45;
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
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(getX(), getY(), getX() -length, getY() -length);
	}
	
}
