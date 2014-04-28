package com.hanfeldt.game;

import java.awt.Graphics;

public class Entity {
	public static final int ticksPerAnimChange = 5; // A shorter name for this would be nice but I can't think of one.jpg
	private int health;
	private int x, y;
	boolean direction = true; // Right = true, Left = false
	Sprite sprite;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	
	public Entity(Sprite s, int h, int x, int y) {
		sprite = s;
		this.health = h;
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void changeX(int change) {
		x += change;
	}
	
	public void changeY(int change) {
		y += change;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void changeHealth(int change) {
		health += change;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void draw(Graphics g) {
		sprite.draw(g, x, y);
	}
}
