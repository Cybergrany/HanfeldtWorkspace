package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;

public class GoreSpawn extends Entity {
	private long lastGoreSpawnTick = 0;
	private ArrayList<Gore> gore;
	private Random rand;
	private  int maxGore;
	private int tickSpawnGoreDelay;
	
	public GoreSpawn(int x, int y) {
		super(x, y);
		rand = new Random();
		gore = new ArrayList<Gore>();
		maxGore = rand.nextInt(16) +3;
		tickSpawnGoreDelay = rand.nextInt(3) +1;
	}
	
	public void tick() {
		if(totalTicks - lastGoreSpawnTick >= tickSpawnGoreDelay && gore.size() < maxGore) {
			gore.add(new Gore(getX() + rand.nextInt(3) -1, getY() + rand.nextInt(3) -1));
			lastGoreSpawnTick = totalTicks;
		}
		for(Gore g : gore) {
			g.tick();
		}
		if(gore.size() > maxGore) {
			Main.getGame().removeGore(this);
			return;
		}
		totalTicks++;
	}
	
	public void render(Graphics g) {
		for(Gore go : gore) {
			go.render(g);
		}
	}
	
}
