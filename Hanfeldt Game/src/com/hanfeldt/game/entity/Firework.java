package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Firework extends Entity {
	private boolean shooting = true;
	private Random rand;
	private int shootingTicks;
	private float angle;
	private Color color;
	private float speed;
	private ArrayList<Point> shootingPoints;
	
	public Firework(int x, int y, float speed) {
		super(x, y);
		this.speed = speed;
		color = Color.ORANGE;
		rand = new Random();
		shootingTicks = rand.nextInt(15) + 15;
		angle = rand.nextInt(70) -35;
		shootingPoints = new ArrayList<Point>();
	}
	
	public void tick() {
		if(shooting) {
			shootingPoints.add(new Point(getX(), getY()));
			velX = (float) (Math.cos(angle) *speed);
			velY = (float) (Math.sin(angle) *speed);
			shootingTicks++;
			if(totalTicks >= shootingTicks) {
				shooting = false;
			}
			changeX(velX);
			changeY(velY);
		}
		totalTicks++;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		for(int i=0; i<shootingPoints.size(); i++) {
			g.fillRect(shootingPoints.get(i).x, shootingPoints.get(i).y, 1, 2);
		}
	}
	
}
