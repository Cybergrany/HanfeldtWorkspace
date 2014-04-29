package com.hanfeldt.game;

import java.awt.Graphics;

public class Player extends Entity {
	
	public Player(Sprite s, int x, int y){
		super(s, 100, x, y); // Health is already set here in le constructor for Entity
		velXMax = 1f;
	}
	
	public void draw(Graphics g) {
		if( (!Main.aDown && !Main.dDown) || (Main.aDown && Main.dDown) || Main.isPaused) {
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
	
	public void tick() {
		if((!Main.dDown && !Main.aDown) || (Main.dDown && Main.aDown)) {
			velX *= 0.9f;
			
			if(velX > 0 && velX < 0.2f) {
				velX = 0;
			}
			if(velX < 0 && velX > -0.2f) {
				velX = 0;
			}
		}else{
			if(Main.aDown) {
				if(velX >= 0) {
					velX = -0.2f;
				}else{
					velX *= 1.1f;
				}
				direction = false;
			}else{
				if(Main.dDown) {
					if(velX <= 0) {
						velX = 0.25f;
					}else{
						velX *= 1.1f;
					}
					direction = true;
				}
			}
			
		}
		
		if(velX < -velXMax) {
			velX = -velXMax;
		}
		if(velX > velXMax) {
			velX = velXMax;
		}
		super.tick();
		
		if(getX() >= (Main.getLevels()[0].getSizeX() - 1) *Main.tileSize) {
			setX((Main.getLevels()[0].getSizeX() - 1) *Main.tileSize);
		}else if(getX() < 0) {
			setX(0);
		}
		
	}
	
}
