package com.hanfeldt.game;

import java.awt.Graphics;

import com.hanfeldt.game.npc.Npc;

public class Entity {
	public static final int ticksPerAnimChange = 4; // A shorter name for this would be nice but I can't think of one
	private int health;
	private float x, y;
	float velX = 0f, velY = 0f;
	float velXMax, velYMax = 3f;
	private int sizeX, sizeY;
	boolean direction = true; // Right = true, Left = false
	boolean falling = false;
	int jumpCount = 0;
	protected Sprite sprite;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	boolean isMovingLeft = false, isMovingRight = false;
	
	public Entity(Sprite s, int h, int x, int y) {
		sprite = s;
		this.health = h;
		this.x = x;
		this.y = y;
		sizeX = sprite.getWidth();
		sizeY = sprite.getHeight();
	}
	
	public void tick(){
		checkCollisions();
		
		if(falling) {
			velY += Main.gravity;
		}else{
			if(velY > 0.0f) {
				velY = 0;
			}
		}
		if(velX < -velXMax) {
			velX = -velXMax;
		}else if(velX > velXMax) {
			velX = velXMax;
		}
		
		if(velY < -Main.terminalVelocity) {
			velY = -Main.terminalVelocity;
		}else if(velY > Main.terminalVelocity) {
			velY = Main.terminalVelocity;
		}
		
		changeX(velX);
		changeY(velY);
		cycleTicks++;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void changeX(float change) {
		x += change;
	}
	
	public void changeY(float change) {
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
		sprite.draw(g, getX(), getY());
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public void checkCollisions() {
		// Most of these aren't working properly. TODO feex
		
		//tile(s) to the right
		try {
			outerLoop:
			for(int i=0; i<getSizeY(); i++) {
				if(Main.getLevels()[0].getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					setTileX(Main.getLevels()[0].getTile(getTileX() +1, getTileY()).getX() -1);
					break outerLoop;
				}
			}
		}catch(Exception e) {}
		
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<getSizeY(); i++) {
					if(Main.getLevels()[0].getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						setTileX(Main.getLevels()[0].getTile(getTileX() -1, getTileY()).getX() +2);
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		
		//tile(s) below
		try {
		outerLoop:
			for(int i=0; i<getSizeX(); i++) {
				if(Main.getLevels()[0].getTile(getTileX() +i, getTileY() + getSizeY()).isSolid()
					|| Main.getLevels()[0].getTile(getTileX() +i +1, getTileY() + getSizeY()).isSolid()) {
					falling = false;
					setTileY(Main.getLevels()[0].getTile(getTileX(), getTileY() + getSizeY()).getY() - getSizeY());
					break outerLoop;
				}
				
				if(i == getSizeX() -1) {
					falling = true;
				}
			}
		}catch(Exception e) {}
		
		//tile(s) above
		try {
			outerLoop:
				for(int i=0; i<getSizeX(); i++) {
					if(Main.getLevels()[0].getTile(getTileX() +i, getTileY()).isSolid()
						|| Main.getLevels()[0].getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Main.getLevels()[0].getTile(getTileX(), getTileY()).getY() +1);
						velY = 0;
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		
	}
	
	public int getTileX() {
		return ((int) x) /Main.tileSize;
	}
	
	public int getTileY() {
		return ((int) y) /Main.tileSize;
	}
	
	public void setTileX(int x) {
		setX(x *Main.tileSize);
	}
	
	public void setTileY(int y) {
		setY(y *Main.tileSize);
	}
	
}
