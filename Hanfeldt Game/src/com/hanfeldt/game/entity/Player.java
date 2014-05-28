package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.event.PlayerEvents;
import com.hanfeldt.game.weapon.Pistol;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.io.Sound;

public class Player extends EntityLiving {
	
	private PlayerEvents events;
	private Weapon weaponEquipped = new Pistol(this, 8, 8, 8, 10);
	static int maxHealth = Values.player_max_health;
	
	public boolean alive = true;
	public boolean levelFinished;
	
	public Player(Sprite s, int x, int y){
		super(s, maxHealth, x, y); // Health is already set here in le constructor for Entity
		velXMax = 1f;
		setJumpHeight(2);
		events = new PlayerEvents(this);
		levelFinished = false;
	}
	
	public void draw(Graphics g) {
		if( (!Main.aDown && !Main.dDown) || (Main.aDown && Main.dDown) || Main.isPaused) {
			sprite.draw(g, (Main.sizeX /2) - (Main.tileSize /2), getY(), direction);
			cycleTicks = 0;
			currentCycle = 0;
		}else{
			sprite.draw(g, (Main.sizeX /2) - (Main.tileSize /2), getY(), direction, currentCycle);
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
		
		//Bounding box, only temporary! Helps test collision detection
		if(Main.debug) {
			g.setColor(Color.RED);
			g.drawRect((Main.sizeX /2) - (Main.tileSize /2), getY(), getSizeX() -1, getSizeY() -1);
		}
		weaponEquipped.draw(g);
	}
	
	public void tick() {
		direction = Main.mouseX > Main.sizeX /2;
		isMovingLeft = Main.aDown;
		isMovingRight = Main.dDown;

		if(Main.wDown && !falling && velY == 0) {
			velY = -getJumpHeight();
			falling = true;
			Sound.playSound("Jump.wav");
		}
		
		// Walk acceleration/slide is below
		if((!isMovingLeft && !isMovingRight) || (isMovingLeft && isMovingRight)) {
			velX *= 0.9f;
			
			if(velX > 0 && velX < 0.2f) {
				velX = 0;
			}
			if(velX < 0 && velX > -0.2f) {
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
		
		if(getY() > Main.sizeY && alive){
			events.damagePlayer(getHealth(), Values.fall_death_id);
			
		}
		
		//Moved fall damage to Entity.checkCollisions (in the "below" section)
		
		//A moment of silence for my jumping code. May it be buried eternally inside those commits
		
		super.tick();
		
		if(getX() >= (Main.getLevels()[Main.getCurrentLevel()].getSizeX() - 1) *Main.tileSize) {
			setX((Main.getLevels()[Main.getCurrentLevel()].getSizeX() - 1) *Main.tileSize);
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
	
}
