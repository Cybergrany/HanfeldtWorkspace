package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;

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
	
	private PlayerEvents events;
	private Weapon weaponEquipped = new Sword(this);
	public static int maxHealth = Values.player_max_health;
	private int money = 100;
	private Listener listener;
	private Main main;
	
	public boolean alive = true;
	public boolean levelFinished;
	
	public Player(Sprite s, int x, int y, Listener l, Main main){
		super(s, maxHealth, x, y); // Health is already set here in le constructor for Entity
		velXMax = (Main.debugCheats ? 10f : 1f);
		setJumpHeight(2);
		events = new PlayerEvents(this);
		levelFinished = false;
		listener = l;
		this.main = main;
	}
	
	public void draw(Graphics g) {
		if( (!listener.aDown && !listener.dDown) || (listener.aDown && listener.dDown)) {
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
		
		if(getY() > Main.sizeY && (main.getState() instanceof Arcade || main.getState() instanceof Story)){
			events.damagePlayer(getHealth(), Values.fall_death_id);
		}
		
		//Moved fall damage to Entity.checkCollisions (in the "below" section)
		
		//A moment of silence for my jumping code. May it be buried eternally inside those commits
		
		super.tick();
		
		if(getX() >= (Story.getLevels()[Story.getCurrentLevel()].getSizeX() - 1) *Main.tileSize) {
			setX((Story.getLevels()[Story.getCurrentLevel()].getSizeX() - 1) *Main.tileSize);
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
	
}
