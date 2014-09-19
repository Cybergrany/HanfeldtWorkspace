package com.hanfeldt.game.entity;

import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.npc.monsters.Zombie;
import com.hanfeldt.game.event.PlayerEvents;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.io.Debug;
import com.hanfeldt.io.Listener;
import com.hanfeldt.io.Sound;

public class Player extends EntityLiving {
	public static final int ticksPerAnimChange = 4;
	private BufferedImage walkingImage;
	private PlayerEvents events;
//	private Weapon weaponEquipped;
	private Weapon weaponEquipped = new Pistol(this);
	public static int maxHealth = Values.player_max_health;
	private int money = 100;
	private int stamina = 69;
	private boolean tired = false;//Time when stamina can't be used during regen
	private long score = 0;
	private Listener listener;
	private Main main;
	
	public boolean alive = true;
	public boolean levelFinished;
	
	public Player(Sprite s, int x, int y, Listener l, Main main){
		super(s, maxHealth, x, y);
		velXMax = (Main.debugCheats ? 10f : 1f);
		setJumpHeight(2);
		events = new PlayerEvents(this);
		levelFinished = false;
		listener = l;
		this.main = main;
	}
	
	public void tickWalking() {
		if( (!listener.aDown && !listener.dDown) || (listener.aDown && listener.dDown)) {
			walkingImage = sprite.getImage(getDirection());
			cycleTicks = 0;
			currentCycle = 0;
		}else{
			walkingImage = sprite.getWalkingImage(direction, currentCycle);
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
	}
	
	public void tick() {
		tickWalking();
		direction = Main.mouseX > Main.WIDTH /2;
		isMovingLeft = listener.aDown;
		isMovingRight = listener.dDown;

		if(listener.wDown && !falling && velY == 0) {
			if(stamina < 0)
				setJumpHeight(1);
			jump();
			if(stamina > 5)
			changestamina(-4);
			Sound.playSound("Jump.wav");
		}
		
		// Walk acceleration/slide is below
		if((!isMovingLeft && !isMovingRight) || (isMovingLeft && isMovingRight)) {
			velX *= 0.93f;
			
			if(velX > 0 && velX < 0.15f) {
				velX = 0;
			}
			if(velX < 0 && velX > -0.15f) {
				velX = 0;
			}
			
		}else{
			if(isMovingLeft) {
				if(velX >= 0) {
					velX = -0.25f;
				}else{
					velX *= 1.1f;
				}
			}else{
				if(isMovingRight) {
					if(velX <= 0) {
						velX = 0.25f;
					}else{
						velX *= 1.1f;
					}
				}
			}
			
		}
		
		if(getY()  > Main.getGame().getLevels().getSizeY() * Main.TILE_SIZE){
			events.damagePlayer(getHealth(), Values.fall_death_id);
			Debug.printDebug("Player out of map");
		}
		
		//Moved fall damage to Entity.checkCollisions (in the "below" section)
		
		super.tick();
		
		if(getX() >= (Main.getGame().getLevels().getSizeX() - 1) *Main.TILE_SIZE) {
			setX((Main.getGame().getLevels().getSizeX() - 1) *Main.TILE_SIZE);
			levelFinished = true;
		}else if(getX() < 0) {
			setX(0);
		}
		
		events.tick();
		
		for(int i=0; i<Main.getGame().getNpc().size(); i++) {
			if(Main.getGame().getNpc().get(i) instanceof Zombie) {
				Zombie zombie = (Zombie) Main.getGame().getNpc().get(i);
				if(collidedZombie(zombie)) {
					events.damagePlayer(Values.zombie_damage_to_player, Values.zombie_damage_id, zombie);
				}
			}
		}
		
		//Bitta sprinting
		if(!Main.debugCheats)
		if(listener.shiftDown && (listener.wDown || listener.dDown)){
			if(!tired)
			velXMax = 2f;
			if(!tired && Main.timer(4)){
				stamina--;
			}
			if(stamina <= - 10){
				tired = true;
				velXMax = 1f;
			}
		}else{
			velXMax = 1f;
			if(stamina < 69 && Main.timer(100)){
				stamina++;
				if(stamina > 10){
					tired = false;
				}
			}
		}
		
		//stamina recovers twice as fast when not moving
		if(stamina < 69 && !isMovingLeft && !isMovingRight && Main.timer(50)){
			stamina++;
		}
		
	}
	
	public boolean collidedZombie(Zombie zombie) {
		return getBounds().intersects(zombie.getBounds());
	}
	
	public PlayerEvents getEvents() {
		return events;
	}
	
	public Weapon getWeaponEquipped() {
		return weaponEquipped;
	}
	
	public void setWeaponEquipped(Weapon wep) {
		weaponEquipped = wep;;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int m) {
		money = m;
	}
	
	public void changeMoney(int c) {
		money += c;
	}
	
	public long getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public void changeScore(int c) {
		score += c;
	}
	
	public int getstamina(){
		return stamina;
	}
	
	public void changestamina(int s){
		stamina += s;
	}
	
	public void setstamina(int s){
		stamina = s;
	}
	
	public BufferedImage getWalkingImage() {
		if(walkingImage == null) {
			return sprite.getImage();
		}else{
			return walkingImage;
		}
	}
	
}
