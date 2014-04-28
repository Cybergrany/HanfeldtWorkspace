package com.hanfeldt.game;

import java.awt.Graphics;

public class Player extends Entity {
	public static int speed = 1;
	public static int x, y, health;
	
	public Player(Sprite s, int x, int y){
		super(s, 100, x, y);
		setHealth(100);//Nice default health
	}
	
	public void tick(){
		if(Main.aDown) {
			changeX(-speed);
//			direction = false;
		}
		
		if(Main.dDown) {
			changeX(speed);
//			direction = true;
		}
		
		x = getX(); y = getY(); health = getHealth();//Again, for debug
	}
	
	public void draw(Graphics g) {
//		sprite.draw(g, (Main.sizeX /2) - (Main.tileSize /2), getY(), direction);
	}
}
