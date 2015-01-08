package com.hanfeldt.game.entity;

import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.weapon.Weapon;

public class EntityLiving extends SpriteEntity {
	public static final int ticksPerAnimChange = 4;
	public Weapon weaponEquipped;
	private BufferedImage walkingImage;
	private int health;
	private float jumpHeight;
	int jumpCount = 0;
	int cycleTicks = 0;
	int currentCycle = 0;
	boolean cycleGoingUp = true;
	
	private boolean pickupItemOnBounds = false;
	
	int aimX, aimY;
	
	public EntityLiving(Sprite s, int h, int x, int y) {
		super(s, x, y);
		this.health = h;
	}
	
	public void tick() {
		super.tick();
		checkTileCollisions();
		try{//TODO optimise. currently checking all items for every entityliving
			for(int i = 0; i < Main.getGame().getItems().size(); i++){
				pickupItemOnBounds(Main.getGame().getItems().get(i));
			}
		}catch(Exception e){}
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
		if(canJump){
			velY = -getJumpHeight();
			falling = true;
		}
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
	
	public Weapon getWeaponEquipped() {
		return weaponEquipped;
	}
	
	public boolean getPickupItemOnBounds(){
		return pickupItemOnBounds;
	}
	
	public void setPickupItemOnBounds(boolean pickup){
		pickupItemOnBounds = pickup;
	}
	
	public void pickupItemOnBounds(EntityItem e){
		if(getBounds().intersects(e.getBounds()) && pickupItemOnBounds){
			if(e.getLinkedItem(this) instanceof Weapon){
				weaponEquipped = (Weapon) e.getLinkedItem(this); 
				e.removeItem(e);
			}
		}
	}
	
}
