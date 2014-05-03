package com.hanfeldt.game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
	public static final int ticksPerAnimChange = 4; // A shorter name for this would be nice but I can't think of one
	private int health;
	private float x, y;
	private Rectangle bounds;
	private float jumpHeight;
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
		checkTileCollisions();
		
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
		isMovingLeft = velX < 0;
		isMovingRight = velX > 0;
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
		sprite.draw(g, getX() - Main.getGame().getPlayer().getX() 
				+ (Main.sizeX /2) - (Main.tileSize /2), getY(), !direction);
	}
	
	public int getSizeX() {
		return sizeX *Main.tileSize;
	}
	
	public int getSizeY() {
		return sizeY *Main.tileSize;
	}
	
	public int getTileSizeX(){
		return sizeX;
	}
	
	public int getTileSizeY() {
		return sizeY;
	}
	
	public void checkTileCollisions() {
		// Most of these aren't working properly. TODO feex
		
		//tile(s) to the right
		try {
			outerLoop:
			for(int i=0; i<getTileSizeY(); i++) {
				if(Main.getLevels()[0].getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					setTileX(Main.getLevels()[0].getTile(getTileX() +1, getTileY()).getX() -1);
					break outerLoop;
				}
			}
		}catch(Exception e) {}
		
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<getTileSizeY(); i++) {
					if(Main.getLevels()[0].getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						setTileX(Main.getLevels()[0].getTile(getTileX() -1, getTileY()).getX() +2);
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		
		//tile(s) below
		try {
		outerLoop:
			for(int i=0; i<getTileSizeX(); i++) {
				if(Main.getLevels()[0].getTile(getTileX() +i, getTileY() + getTileSizeY()).isSolid()
					|| Main.getLevels()[0].getTile(getTileX() +i +1, getTileY() + getTileSizeY()).isSolid()) {
					falling = false;
					if(velY >= Main.terminalVelocity - 1) {
						if(this instanceof Player) {
							((Player) this).getEvents().damagePlayer((int) velY, 1);
						}
					}
					setTileY(Main.getLevels()[0].getTile(getTileX(), getTileY() + getTileSizeY()).getY() - getTileSizeY());
					break outerLoop;
				}
				
				if(i == getTileSizeX() -1) {
					falling = true;
				}
			}
		}catch(Exception e) {}
		
		//tile(s) above
		try {
			outerLoop:
				for(int i=0; i<getTileSizeX(); i++) {
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
	
	public void setVelX(float vx) {
		velX = vx;
	}
	
	public void setVelY(float vy) {
		velY = vy;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public void setVelXMax(float vxm) {
		velXMax = vxm;
	}
	
	public boolean isMovingRight() {
		return isMovingRight;
	}
	
	public boolean isMovingLeft() { 
		return isMovingLeft;
	}
	
	public void setDirection(boolean dir) {
		direction = dir;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getSizeX(), getSizeY());
	}
	
	public void setJumpHeight(float jh) {
		jumpHeight = jh;
	}
	
	public float getJumpHeight() {
		return jumpHeight;
	}
	
}
