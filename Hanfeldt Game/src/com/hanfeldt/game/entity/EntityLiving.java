package com.hanfeldt.game.entity;

import java.awt.image.BufferedImage;

import com.hanfeldt.game.display.Sprite;

public class EntityLiving extends SpriteEntity {
	public static final int ticksPerAnimChange = 4;
	private BufferedImage walkingImage;
	private int health;
	private float jumpHeight;
	int jumpCount = 0;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	protected boolean isCollidingWithHorizTile=false;
	
	int aimX, aimY;
	
	public EntityLiving(Sprite s, int h, int x, int y) {
		super(s, x, y);
		this.health = h;
	}
	
	public void tick() {
		super.tick();
		checkTileCollisions(this);
		cycleTicks++;
	}
	
	public void setJumpHeight(float jh) {
		jumpHeight = jh;
	}
	
	public float getJumpHeight() {
		return jumpHeight;
	}
	
	public void tickWalking(){
		walkingImage = sprite.getWalkingImage(!direction, currentCycle);
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
	
	public int getAimX(){
		return aimX;
	}
	
	public void setAimX(int aX){
		aimX = aX;
	}
	
	public int getAimY(){
		return aimY;
	}
	
	public void setAimY(int aY){
		aimY = aY;
	}
	
	public BufferedImage getWalkingImage() {
		if(walkingImage == null) {
			return sprite.getImage();
		}else{
			return walkingImage;
		}
	}
	
}
