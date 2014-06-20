package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.event.NpcEvents;

public class Zombie extends Npc {
	private float speed = 0.25f;
	private static Sprite s = new Sprite(Main.spriteSheet, 2, 3, 1, 2);//Same as player for testing
	
	
	public static int maxHealth = Values.zombie_max_health;
	private NpcEvents events;

	public Zombie(int x, int y) {
		super(s, maxHealth, x, y);
		setVelXMax(speed);
		events = new NpcEvents(this);
		setJumpHeight(2.3f);//Slightly higher than block so they can jump over them with ease
	}
	
	public void tick() {
		super.tick();
		if(Main.timer(120)) {
			facePlayer(speed);
		}
		if(Main.timer(60)){//Checks if zombie needs to jump more often so they don't stay still as much
			if(isCollidingWithHorizTile() && !getFalling()){
				jump();
				if(events.isOutsideScreen(this)){
					events.idle(this, speed);
				}else{
					facePlayer(speed);//Forward momentum towards player
				}
				isCollidingWithHorizTile = false;
			}
		}
		events.tick();
	}
	
	public static int getMaxNpc(){
		return Values.max_zombie;
	}
	
}
