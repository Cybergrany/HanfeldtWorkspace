package com.hanfeldt.game.entity;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.tile.Tile;

public class Entity {
	protected long totalTicks = 0;
	private float x, y;
	float velX = 0f, velY = 0f;
	float velXMax = 10f, velYMax = 3f;
	protected boolean direction = true; //Right = true, Left = false
	boolean falling = false;
	boolean isMovingLeft = false, isMovingRight = false;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		if(falling) {
			velY += Main.GRAVITY;
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
		
		if(velY < -Main.TERMINAL_VELOCITY) {
			velY = -Main.TERMINAL_VELOCITY;
		}else if(velY > Main.TERMINAL_VELOCITY) {
			velY = Main.TERMINAL_VELOCITY;
		}
		
		changeX(velX);
		changeY(velY);
		isMovingLeft = velX < 0;
		isMovingRight = velX > 0;
		totalTicks++;
	}
	
	public void checkTileCollisions(EntityLiving e) {
		// Most of these aren't working properly. TODO feex
		
		//tile(s) to the right
		try {
			outerLoop:
			for(int i=0; i<e.getTileSizeY(); i++) {
				if(Main.getGame().getLevels().getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					e.isCollidingWithHorizTile=true;
					setTileX(Main.getGame().getLevels().getTile(getTileX() +1, getTileY()).getTileX() -1);
					break outerLoop;
				}
			}
		}catch(Exception er) {}
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<e.getTileSizeY(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						e.isCollidingWithHorizTile=true;
						setTileX(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileX() +1);
						break outerLoop;
					}
				}
		}catch(Exception er) {}
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<e.getTileSizeX(); i++) {
				Tile tileBelow = Main.getGame().getLevels().getTile(getTileX() +i, getTileY() + e.getTileSizeY());
				tileBelow.onCollidedEntity(this);
				boolean tileBelowSolid = tileBelow.isSolid();
				boolean tileBelowRightSolid = Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY() + e.getTileSizeY()).isSolid();
				boolean tileBelowLeftSolid = Main.getGame().getLevels().getTile(getTileX() +i -1, getTileY() + e.getTileSizeY()).isSolid();
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
					setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY() + e.getTileSizeY()).getTileY() - e.getTileSizeY());
					break outerLoop;
				}
				if(i == e.getTileSizeX() -1) {
					falling = true;
				}
			}
		}catch(Exception er) {}
		
		//tile(s) above
		try {
			outerLoop:
				for(int i=0; i<e.getTileSizeX(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX() +i, getTileY()).isSolid()
						|| Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileY() +1);
						velY = 0;
						break outerLoop;
					}
				}
		}catch(Exception er) {}
		
	}
	
	public void checkTileCollisions(EntityItem e) {
		// Most of these aren't working properly. TODO feex
		
		//tile(s) to the right
		try {
			outerLoop:
			for(int i=0; i<e.getTileSizeY(); i++) {
				if(Main.getGame().getLevels().getTile(getTileX() +1, getTileY() +i).isSolid()
					&& isMovingRight) {
					velX = 0;
					e.isCollidingWithHorizTile=true;
					setTileX(Main.getGame().getLevels().getTile(getTileX() +1, getTileY()).getTileX() -1);
					break outerLoop;
				}
			}
		}catch(Exception er) {}
		//tile(s) to the left
		try {
			outerLoop:
				for(int i=0; i<e.getTileSizeY(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX(), getTileY() +i).isSolid()
						&& isMovingLeft) {
						velX = 0;
						e.isCollidingWithHorizTile=true;
						setTileX(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileX() +1);
						break outerLoop;
					}
				}
		}catch(Exception er) {}
		//tile(s) below
		//TODO Be able to fall down 1x1 hole
		try {
		outerLoop:
			for(int i=0; i<e.getTileSizeX(); i++) {
				Tile tileBelow = Main.getGame().getLevels().getTile(getTileX() +i, getTileY() + e.getTileSizeY());
				tileBelow.onCollidedEntity(this);
				boolean tileBelowSolid = tileBelow.isSolid();
				boolean tileBelowRightSolid = Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY() + e.getTileSizeY()).isSolid();
				boolean tileBelowLeftSolid = Main.getGame().getLevels().getTile(getTileX() +i -1, getTileY() + e.getTileSizeY()).isSolid();
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
					setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY() + e.getTileSizeY()).getTileY() - e.getTileSizeY());
					break outerLoop;
				}
				if(i == e.getTileSizeX() -1) {
					falling = true;
				}
			}
		}catch(Exception er) {}
		
		//tile(s) above
		try {
			outerLoop:
				for(int i=0; i<e.getTileSizeX(); i++) {
					if(Main.getGame().getLevels().getTile(getTileX() +i, getTileY()).isSolid()
						|| Main.getGame().getLevels().getTile(getTileX() +i +1, getTileY()).isSolid()) {
						setTileY(Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileY() +1);
						velY = 0;
						break outerLoop;
					}
				}
		}catch(Exception er) {}
		
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
	
	public int getTileX() {
		return ((int) x) /Main.TILE_SIZE;
	}
	
	public int getTileY() {
		return ((int) y) /Main.TILE_SIZE;
	}
	
	public void setTileX(int x) {
		setX(x *Main.TILE_SIZE);
	}
	
	public void setTileY(int y) {
		setY(y *Main.TILE_SIZE);
	}
	
	public void changeVelX(float change){
		velX += change;
	}
	
	public void changeVelY(float change){
		velY += change;
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
	/**
	 * Right = true, Left = false
	 */
	public void setDirection(boolean dir) {//JavaDoc shows up in autocomplete! // (Ronan) Oh right
		direction = dir;
	}
	
	public boolean getDirection() {
		return direction;
	}
	
	public int getSizeX() {
		return getX() *Main.TILE_SIZE;
	}
	
	public int getSizeY() {
		return getY() *Main.TILE_SIZE;
	}
	
}
