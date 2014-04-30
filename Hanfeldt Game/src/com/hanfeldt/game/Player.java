package com.hanfeldt.game;

import java.awt.Graphics;

public class Player extends Entity {
	
	static int maxHealth = 100;
	int jumpHeight = 4; float jumpSpeed = 1f; boolean jumped;
	
	public Player(Sprite s, int x, int y){
		super(s, maxHealth, x, y); // Health is already set here in le constructor for Entity
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
		isMovingLeft = Main.aDown;
		isMovingRight = Main.dDown;
		// Walk acceleration/slide is below
		if((!isMovingLeft && !isMovingRight) || (isMovingLeft && isMovingRight)) {
			velX *= 0.9f;
			
			if(velX > 0 && velX < 0.2f) {
				velX = 0;
			}
			if(velX < 0 && velX > -0.2f) {
				velX = 0;
			}
		}else{
			if(isMovingLeft) {
				if(velX >= 0) {
					velX = -0.25f;
				}else{
					velX *= 1.1f;
				}
				direction = false;
			}else{
				if(isMovingRight) {
					if(velX <= 0) {
						velX = 0.25f;
					}else{
						velX *= 1.1f;
					}
					direction = true;
				}
			}
			
		}
		
		if(Main.wDown && !falling) {
			velY = -Main.terminalVelocity;
			falling = true;
		}
		
		/* Daith�s jump code
		if(Main.wDown){
			if(jumpCount >= jumpHeight && getY() < 96){
				velY+=jumpSpeed;
				jumped = true;
			}else if(!jumped){
				velY-=jumpSpeed;
				jumpCount++;
			}
			if(getY() >= 96){
				setY(96);
				jumpCount = 0;
			}
		}else if(getY() < 96){
			velY+=jumpSpeed;
		}else{
			jumpCount = 0;
			velY = 0;
			jumped = false;
			setY(96);
		}
		*/
		
		super.tick();
		
		if(getX() >= (Main.getLevels()[0].getSizeX() - 1) *Main.tileSize) {
			setX((Main.getLevels()[0].getSizeX() - 1) *Main.tileSize);
		}else if(getX() < 0) {
			setX(0);
		}
		
	}
	
}
