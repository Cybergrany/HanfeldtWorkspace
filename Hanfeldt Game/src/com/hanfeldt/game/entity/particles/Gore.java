package com.hanfeldt.game.entity.particles;

import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.tile.Tile;

public class Gore extends Entity {
	
	private float yOff;

	public Gore(int x, int y) {
		super(x, y);
		falling = true;
		Random rand = new Random();
		velX = rand.nextFloat() *2.5f * (rand.nextBoolean() ? 1 : -1);
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
//					tileBelow.onCollidedEntity(this);
					boolean tileBelowSolid = tileBelow.isSolid();
//					System.out.println(tileBelowSolid);
					if(tileBelowSolid) {
						falling = false;
//						System.out.println("Stopped");
						setVelX(0);
						setY((Main.getGame().getLevels().getTile(getTileX(), getTileY()).getTileY() + (yOff) / 6/*Depth of blood on tile*/) * Main.TILE_SIZE);
						break outerLoop;
					}
//					if(i == getSizeX() -1) {
//						falling = true;
//					}
				}
			}catch(Exception er) {;}
	}
}
