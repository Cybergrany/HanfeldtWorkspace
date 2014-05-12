package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class EntityLiving extends Entity {
	public static final int ticksPerAnimChange = 4; // A shorter name for this would be nice but I can't think of one
	private int health;
	private float jumpHeight;
	private int sizeX, sizeY;
	int jumpCount = 0;
	protected Sprite sprite;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	boolean isCollidingWithHorizTile=false;
	
	public EntityLiving(Sprite s, int h, int x, int y) {
		super(x, y);
		sprite = s;
		this.health = h;
		sizeX = sprite.getWidth();
		sizeY = sprite.getHeight();
	}
	
	public void tick() {
		super.tick();
		checkTileCollisions();
		cycleTicks++;
	}
	
	public void setJumpHeight(float jh) {
		jumpHeight = jh;
	}
	
	public float getJumpHeight() {
		return jumpHeight;
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
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getSizeX(), getSizeY());
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
					isCollidingWithHorizTile=true;
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
						isCollidingWithHorizTile=true;
						setTileX(Main.getLevels()[0].getTile(getTileX(), getTileY()).getX() +1);
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<getTileSizeX(); i++) {
				boolean tileBelow = Main.getLevels()[0].getTile(getTileX() +i, getTileY() + getTileSizeY()).isSolid();
				boolean tileBelowRight = Main.getLevels()[0].getTile(getTileX() +i +1, getTileY() + getTileSizeY()).isSolid();
				if( (tileBelow || tileBelowRight) ) {
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
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void changeHealth(int change) {
		health += change;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setFalling(boolean f){
		falling = f;
	}
	
	public boolean getFalling(){
		return falling;
	}
	
	public boolean isCollidingWithHorizTile(){
		return isCollidingWithHorizTile;
	}
	
}
