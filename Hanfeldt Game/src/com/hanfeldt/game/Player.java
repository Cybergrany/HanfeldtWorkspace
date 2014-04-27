package com.hanfeldt.game;

import java.awt.Graphics;

public class Player extends Entity {
	public static int speed = 1;
	
	public Player(Sprite s, int x, int y){
		super(s, 100, x, y);
	}
	
	public void tick(){
		if(Main.aDown) {
			changeX(-speed);
		}
		
		if(Main.dDown) {
			changeX(speed);
		}
		
		//System.out.println(getX());
	}
	
	public void draw(Graphics g) {
		sprite.draw(g, (Main.sizeX /2) - Main.tileSize, getY());
		//sprite.draw(g, getX(), getY());
	}
}
