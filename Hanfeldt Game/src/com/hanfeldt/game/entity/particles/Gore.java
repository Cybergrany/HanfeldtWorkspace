package com.hanfeldt.game.entity.particles;

import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.tile.Tile;

public class Gore extends Entity {
	
	private float yOff;

	public Gore(int x, int y, int layer) {
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (rand.nextBoolean() ? 1 : -1);
		velY = rand.nextFloat() *-1.5f;
		yOff = rand.nextFloat();
	}
	
	public Gore(int x, int y, boolean dir, int layer){
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (dir ? -1 : 1);
		velY = rand.nextFloat() *-1.5f;
		yOff = rand.nextFloat();
	}
	
	public void tick() {
		super.tick();
		checkLowerCollisions();
	}
	
	public void checkLowerCollisions(){
		try {
			outerLoop:
				for(int i=0; i<1; i++) {
					Tile tileBelow = Main.getGame().getLevels().getTile(getTileX() +i, getTileY());
					boolean tileBelowSolid = tileBelow.isSolid();
					if(tileBelowSolid) {
						falling = false;
						setVelX(0);
						setY((Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileY() + (yOff) / 9/*Depth of blood on tile*/) * Main.TILE_SIZE);
						break outerLoop;
					}
				}
			}catch(Exception er) {;}
	}
}
