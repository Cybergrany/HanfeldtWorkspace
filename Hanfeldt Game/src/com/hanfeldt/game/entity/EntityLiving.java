package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.tile.Tile;

public class EntityLiving extends Entity {
	public static final int ticksPerAnimChange = 4;
	private BufferedImage walkingImage;
	private int health;
	private float jumpHeight;
	private int sizeX, sizeY;
	int jumpCount = 0;
	protected Sprite sprite;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	protected boolean isCollidingWithHorizTile=false;
	
	int aimX, aimY;
	
	public EntityLiving(Sprite s, int h, int x, int y) {
		super(x, y);
		sprite = s;
		this.health = h;
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
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
	
	public Sprite getSprite() {
		return sprite;
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
	
	public Sprite getReverseSprite() {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(sprite.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}
	
	public BufferedImage getWalkingImage() {
		if(walkingImage == null) {
			return sprite.getImage();
		}else{
			return walkingImage;
		}
	}
	
}
