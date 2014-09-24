package com.hanfeldt.game.entity.particles;

import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.entity.Entity;

public class GoreSpawn extends Entity {
	private long lastGoreSpawnTick = 0;
	public ArrayList<Gore> gore;
	private Random rand;
	private  int maxGore;
	private int tickSpawnGoreDelay;
	private boolean singleDir = false, direction;
	
	public GoreSpawn(int x, int y) {
		super(x, y);
		rand = new Random();
		gore = new ArrayList<Gore>();
		maxGore = rand.nextInt(16) +3;
		tickSpawnGoreDelay = rand.nextInt(3) +1;
	}
	
	public GoreSpawn(int x, int y, boolean dir) {
		super(x, y);
		singleDir = true;//Signifies that gore will travel in one direction
		rand = new Random();
		gore = new ArrayList<Gore>();
		maxGore = rand.nextInt(16) +3;
		tickSpawnGoreDelay = rand.nextInt(3) +1;
		direction = !dir;
	}
	
	public void tick() {
		if(totalTicks - lastGoreSpawnTick >= tickSpawnGoreDelay && gore.size() < maxGore) {
			if(singleDir){
				gore.add(new Gore(getX() + rand.nextInt(3) -1, getY() + rand.nextInt(3) -1, direction));
			}else{
				gore.add(new Gore(getX() + rand.nextInt(3) -1, getY() + rand.nextInt(3) -1));
			}
			lastGoreSpawnTick = totalTicks;
		}
		for(Gore g : gore) {
			g.tick();
		}
		if(gore.size() > maxGore) {
			removeGore();
			return;
		}
		totalTicks++;
	}
	
	/**
	 * One does not simply access the arrayList in main
	 * and remove elements while it is being iterated over.
	 */
	public void removeGore(){
		gore.remove(this);
	}
}
