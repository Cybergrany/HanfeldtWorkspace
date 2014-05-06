package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;

public class Entity {
	private float x, y;
	private Rectangle bounds;
	float velX = 0f, velY = 0f;
	float velXMax, velYMax = 3f;
	boolean direction = true; // Right = true, Left = false
	boolean falling = false;
	boolean isMovingLeft = false, isMovingRight = false;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
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
	
	public boolean getDirection() {
		return direction;
	}
	
}