package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Tile;

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
	protected boolean isCollidingWithHorizTile=false;
	
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
		int screenX = getX() - Main.getGame().getPlayer().getX() 
				+ (Main.sizeX /2) - (Main.tileSize /2);
		if(screenX + getSizeX() > 0 && screenX < Main.sizeX){
			sprite.draw(g, screenX, getY(), !direction);
		}
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
				if(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					isCollidingWithHorizTile=true;
					setTileX(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +1, getTileY()).getX() -1);
					break outerLoop;
				}
			}
		}catch(Exception e) {}
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<getTileSizeY(); i++) {
					if(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						isCollidingWithHorizTile=true;
						setTileX(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY()).getX() +1);
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<getTileSizeX(); i++) {
				Tile tileBelow = Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i, getTileY() + getTileSizeY());
				tileBelow.onCollidedEntity(this);
				boolean tileBelowSolid = tileBelow.isSolid();
				boolean tileBelowRightSolid = Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i +1, getTileY() + getTileSizeY()).isSolid();
				boolean tileBelowLeftSolid = Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i -1, getTileY() + getTileSizeY()).isSolid();
				// I know this if statement is shitey, fix it if you can think of a better solution
				if( (tileBelowSolid || tileBelowRightSolid) 
					//Next line accounts for 1x1 hole
					&& !(!tileBelowSolid && tileBelowRightSolid && tileBelowLeftSolid && getX() % Main.tileSize == 0) ) {
					falling = false;
					if(velY >= Main.terminalVelocity - 1) {
						if(this instanceof Player) {
							((Player) this).getEvents().damagePlayer((int) velY, 1);
						}
					}
					setTileY(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY() + getTileSizeY()).getY() - getTileSizeY());
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
					if(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i, getTileY()).isSolid()
						|| Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Story.getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY()).getY() +1);
						velY = 0;
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		
	}
	
	public void jump(){
		velY = -getJumpHeight();
		falling = true;
		
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
