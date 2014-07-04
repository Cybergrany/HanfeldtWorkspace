package com.hanfeldt.game.entity;

import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.event.PlayerEvents;
import com.hanfeldt.game.state.Arcade;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Sword;
import com.hanfeldt.io.Listener;
import com.hanfeldt.io.Sound;

public class Player extends EntityLiving {
	public static final int ticksPerAnimChange = 4;
	private BufferedImage walkingImage;
	private PlayerEvents events;
	private Weapon weaponEquipped = new Sword(this);
	public static int maxHealth = Values.player_max_health;
	private int money = 100;
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
			jump();
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
		
		if(getY() > Main.HEIGHT && (main.getState() instanceof Arcade || main.getState() instanceof Story)){
			events.damagePlayer(getHealth(), Values.fall_death_id);
		}
		
		//Moved fall damage to Entity.checkCollisions (in the "below" section)
		
		//A moment of silence for my jumping code. May it be buried eternally inside those commits
		
		super.tick();
		
		if(getX() >= (Main.getGame().getLevels()[Story.getCurrentLevel()].getSizeX() - 1) *Main.tileSize) {
			setX((Main.getGame().getLevels()[Story.getCurrentLevel()].getSizeX() - 1) *Main.tileSize);
			levelFinished = true;
		}else if(getX() < 0) {
			setX(0);
		}
		
		events.tick();
		
		for(int i=0; i<Main.npc.size(); i++) {
			if(Main.npc.get(i) instanceof Zombie) {
				Zombie zombie = (Zombie) Main.npc.get(i);
				if(collidedZombie(zombie)) {
					events.damagePlayer(Values.zombie_damage_to_player, Values.zombie_damage_id, zombie);
				}
			}
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
	
	public BufferedImage getWalkingImage() {
		if(walkingImage == null) {
			return sprite.getImage();
		}else{
			return walkingImage;
		}
	}
	
}
