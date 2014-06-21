package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;

public class GoreSpawn extends Entity {
	private static final int tickSpawnGoreDelay = 5;
	private static final int maxGore = 16;
	private static final int lifeTime = 180;
	private long lastGoreSpawnTick = 0;
	private ArrayList<Gore> gore;
	private Random rand;
	
	public GoreSpawn(int x, int y) {
		super(x, y);
		rand = new Random();
		gore = new ArrayList<Gore>();
	}
	
	public void tick() {
		if(totalTicks - lastGoreSpawnTick >= tickSpawnGoreDelay && gore.size() < maxGore) {
			gore.add(new Gore(getX() + rand.nextInt(3) -1, getY() + rand.nextInt(3) -1));
			lastGoreSpawnTick = totalTicks;
		}
		for(Gore g : gore) {
			g.tick();
		}
		if(totalTicks > lifeTime) {
			Main.getGame().removeGore(this);
			return;
		}
		totalTicks++;
	}
	
	public void render(Graphics g) {
		System.out.println("Rendered gorespawn.");
		for(Gore go : gore) {
			go.render(g);
		}
	}
	
}
