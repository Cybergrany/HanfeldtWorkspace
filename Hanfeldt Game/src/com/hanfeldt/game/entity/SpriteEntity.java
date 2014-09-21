package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.tile.Tile;

/***
 * OOP FTW
 * This is going to save a load of typing, and make the code cleaner what with the amount of Entities that need sprites.
 * @author ofeldt
 *
 */
public class SpriteEntity extends Entity{
	
	public Sprite sprite;
	public int sizeX, sizeY;
	public boolean isCollidingWithHorizTile;
	public boolean canJump= true;//Mostly for living entities, but having it here is convenient, as entityLiving extends SpriteEntity
	
	/**
	 * Needed for entities that do not have a sprite in some cases.
	 * For example, Bullets
	 * @param x
	 * @param y
	 */
	public SpriteEntity(int x, int y) {
		super(x, y);
	}

	public SpriteEntity(Sprite s, int x, int y){
		super(x, y);
		sprite = s;
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
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
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Rectangle getBounds(){
		Rectangle r = new Rectangle(sizeX, sizeY, getSizeX(), getSizeY());
		r.setLocation(getX(), getY());
		return r;
	}
	
	public Sprite getReverseSprite() {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(sprite.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}
	
	public Sprite getReverseSprite(Sprite s) {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(s.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}
	
	public void checkTileCollisions() {
		// Most of these aren't working properly. TODO feex
		
		//tile(s) to the right
		try {
			outerLoop:
			for(int i=0; i<getTileSizeY(); i++) {
				if(Main.getGame().getLevels().getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					isCollidingWithHorizTile=true;
					setTileX(Main.getGame().getLevels().getTile(getTileX() +1, getTileY()).getTileX() -1);
					break outerLoop;
				}
			}
		}catch(Exception er) {}
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<getTileSizeY(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						isCollidingWithHorizTile=true;
						setTileX(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileX() +1);
						break outerLoop;
					}
				}
		}catch(Exception er) {}
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<getTileSizeX(); i++) {
				Tile tileBelow = Main.getGame().getLevels().getTile(getTileX() +i, getTileY() + getTileSizeY());
				tileBelow.onCollidedEntity(this);
				boolean tileBelowSolid = tileBelow.isSolid();
				boolean tileBelowRightSolid = Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY() + getTileSizeY()).isSolid();
				boolean tileBelowLeftSolid = Main.getGame().getLevels().getTile(getTileX() +i -1, getTileY() + getTileSizeY()).isSolid();
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
					setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY() + getTileSizeY()).getTileY() - getTileSizeY());
					break outerLoop;
				}
				if(i == getTileSizeX() -1) {
					falling = true;
				}
			}
		}catch(Exception er) {}
		
		//tile(s) above
		try {
			outerLoop:
				for(int i=0; i<getTileSizeX(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX() +i, getTileY()).isSolid()
						|| Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileY() +1);
						velY = 0;
						canJump = false;
						break outerLoop;
					}else{
						canJump = true;
					}
				}
		}catch(Exception er) {}
		
	}

}
