package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.util.Random;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.CementCore;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.CementRoof;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class LevelArcade extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	public LevelArcade(Player p) {
		sizeY = Main.HEIGHT / Main.TILE_SIZE;
		sizeX = Main.WIDTH / Main.TILE_SIZE * 5;
		tiles = new Tile[sizeX][sizeY];
		spawner = new Spawner();
		
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				tiles[j][i] = new Air(j, i);	
			}
		}
		
		generateLevel(p);
		
		setBg(level);
		
		player = p;
	}
	
	public void generateLevel(Player p){
		
		//Rough Terrain
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(y > sizeY - 2){
					
					//Floor (With gaps)
					if(new Random().nextInt(100) > 10){
						try{
							tiles[x][y] = new CementCore(x, y);
						}catch(Exception e){}
					}
					
					
					
					if(new Random().nextInt(100) > 90){
						try{
							int r = new Random().nextInt(sizeY - 6);
							for(int i = 0; i <= r; i++){
								tiles[x][y - i] = new CementFloor(x, y - i);
							}
						}catch(Exception e){}
					}
				}
				
				
				if (new Random().nextInt(100) > 80){
					try{
						if(tiles[x][y] instanceof CementCore)
						tiles[x][y - 1] = new CementCore(x, y - 1);
					}catch(Exception e){}
				}
			}
		}
		
		//Roof
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(y < 1){
					if(new Random().nextInt(100) > 10){
						try{
							tiles[x][y] = new CementRoof(x, y);
						}catch(Exception e){}
					}
				}
			}
		}
		
		
		//Corrections
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				//Smooth out floor
				try{
					if(tiles[x][y] instanceof CementCore && tiles[x][y - 1] instanceof Air){
							tiles[x][y] = new CementFloor(x, y);
					}
				}catch(Exception e){}
				//Left-hand side wall
				if(x < 1){
					tiles[x][y] = new CementCore(x, y);
				}
			}
		}
		
		//Spawners
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(new Random().nextInt(100) > 80){
					try{
						for(int i = 0; i <= 2; i++)
						if(tiles[x] [y + i] instanceof CementCore && tiles[x][y] instanceof Air){
							tiles[x][y] = new ZombieSpawner(x, y);
						}
					}catch(Exception e){}
				}
			}
		}
		
		//Spawn Zombies
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				if(tiles[x][y] instanceof ZombieSpawner)
				for(int i2 = 0; i2 < Zombie.getMaxNpc(); i2++) {
					//TODO: Regular spawning, not just on level creation.
					spawner.spawnNpc(new Zombie(Main.TILE_SIZE *x + (i2*30), Main.TILE_SIZE * y - 40));
				}
			}
		}
	}
	
	public void tick(){
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		bg.draw(g);
		super.render(g, c);
	}
	
	
}
