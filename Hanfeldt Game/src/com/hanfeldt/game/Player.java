package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.events.PlayerEvents;

public class Player extends Entity{
	
	private PlayerEvents events;
	
	static int maxHealth = 100;
	int jumpHeight = 4; float jumpSpeed = 1f; boolean jumped;
	
	public boolean alive = true;
	
	public Player(Sprite s, int x, int y){
		super(s, maxHealth, x, y); // Health is already set here in le constructor for Entity
		velXMax = 1f;
		events = new PlayerEvents(this);
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
		
		//Bounding box, only temporary!
		g.setColor(Color.RED);
		g.drawRect((Main.sizeX /2) - (Main.tileSize /2), getY(), getSizeX() *Main.tileSize, getSizeY() *Main.tileSize);
	}
	
	public void tick() {
		isMovingLeft = Main.aDown;
		isMovingRight = Main.dDown;

		if(Main.wDown && !falling) {
			velY = -Main.terminalVelocity;
			falling = true;
		}
		
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
		
		if(getY() > Main.sizeY && alive){
			events.damagePlayer(getHealth(), events.fallDamage);
		}
		
		if(velY > Main.terminalVelocity - .001){
			//TODO:Serious improvements needed on this fall damage yoke.
//			events.damagePlayer(10, events.fallDamage);
		}
		
		//A moment of silence for my jumping code. May it be buried eternally inside those commits
		
		super.tick();
		
		if(getX() >= (Main.getLevels()[0].getSizeX() - 1) *Main.tileSize) {
			setX((Main.getLevels()[0].getSizeX() - 1) *Main.tileSize);
		}else if(getX() < 0) {
			setX(0);
		}
		
		if(Main.wDown && !falling) {
			velY = -Main.terminalVelocity;
			falling = true;
		}
		
		events.tick();
	}
}
