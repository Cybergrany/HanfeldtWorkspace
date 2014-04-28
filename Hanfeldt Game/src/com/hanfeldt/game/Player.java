package com.hanfeldt.game;

import java.awt.Graphics;

public class Player extends Entity {
	public static int speed = 1;
	
	public Player(Sprite s, int x, int y){
		super(s, 100, x, y); // Health is already set here in le constructor for Entity
	}
	
	public void tick(){
		if(Main.aDown) {
			changeX(-speed);
			direction = false;
		}
		
		if(Main.dDown) {
			changeX(speed);
			direction = true;
		}
		
		cycleTicks++;
	}
	
	public void draw(Graphics g) {
		if(!Main.aDown && ! Main.dDown) {
			sprite.draw(g, (Main.sizeX /2) - (Main.tileSize /2), getY(), direction);
			cycleTicks = 0;
			currentCycle = 0;
		}else{
			sprite.draw(g, (Main.sizeX /2) - (Main.tileSize /2), getY(), direction, currentCycle);
			if(cycleTicks >= ticksPerAnimChange) {
				if(currentCycle >= sprite.getWalkingAnimsLength() -1 && cycleGoingUp) {
					cycleGoingUp = false;
				}else if(currentCycle == 0 && !cycleGoingUp){
					cycleGoingUp = true;
				}
				
				if(cycleGoingUp) {
					currentCycle++;
				}else{
					currentCycle--;
				}
				
				cycleTicks = 0;
			}
		}
	}
}
