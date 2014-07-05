package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Tile;

public class EntityLiving extends Entity {
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
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
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
	public int getSizeX() {
		return sizeX *Main.TILE_SIZE;
	}
	
	public int getSizeY() {
		return sizeY *Main.TILE_SIZE;
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
				if(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					isCollidingWithHorizTile=true;
					setTileX(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +1, getTileY()).getTileX() -1);
					break outerLoop;
				}
			}
		}catch(Exception e) {}
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<getTileSizeY(); i++) {
					if(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						isCollidingWithHorizTile=true;
						setTileX(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY()).getTileX() +1);
						break outerLoop;
					}
				}
		}catch(Exception e) {}
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<getTileSizeX(); i++) {
				Tile tileBelow = Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i, getTileY() + getTileSizeY());
				tileBelow.onCollidedEntity(this);
				boolean tileBelowSolid = tileBelow.isSolid();
				boolean tileBelowRightSolid = Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i +1, getTileY() + getTileSizeY()).isSolid();
				boolean tileBelowLeftSolid = Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i -1, getTileY() + getTileSizeY()).isSolid();
				// I know this if statement is shitey, fix it if you can think of a better solution
				if( (tileBelowSolid || tileBelowRightSolid) 
					//Next line accounts for 1x1 hole
					&& !(!tileBelowSolid && tileBelowRightSolid && tileBelowLeftSolid && getX() % Main.TILE_SIZE == 0) ) {
					falling = false;
					if(velY >= Main.TERMINAL_VELOCITY - 1) {
						if(this instanceof Player) {
							((Player) this).getEvents().damagePlayer((int) velY, 1);
						}
					}
					setTileY(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY() + getTileSizeY()).getTileY() - getTileSizeY());
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
					if(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i, getTileY()).isSolid()
						|| Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Main.getGame().getLevels()[Story.getCurrentLevel()].getTile(getTileX(), getTileY()).getTileY() +1);
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
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Sprite getReverseSprite() {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(sprite.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}
	
}
