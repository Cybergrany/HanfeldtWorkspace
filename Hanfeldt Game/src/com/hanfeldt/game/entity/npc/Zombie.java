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
	}
	
	public void tick() {
		super.tick();
		if(Main.getGame().getTotalTicks() % 120 == 0) {
			facePlayer(speed);
		}
		events.tick();
	}
	
	public static int getMaxNpc(){
		return Values.max_zombie;
	}
	
}
