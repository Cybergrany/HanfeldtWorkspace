package com.hanfeldt.game.entity.particles;

import java.util.Random;

import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;

public class Gore extends Entity {

	public Gore(int x, int y) {
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (rand.nextBoolean() ? 1 : -1);
		velY = rand.nextFloat() *-1.5f;
	}
	
	public void tick() {
		super.tick();
	}
}
