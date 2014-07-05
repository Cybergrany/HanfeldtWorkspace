package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.util.Random;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.CementCore;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.Tile;

public class LevelArcade extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	public LevelArcade(Player p) {
		sizeY = Main.HEIGHT / Main.TILE_SIZE;
		sizeX = Main.WIDTH / Main.TILE_SIZE * 2;
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
					
					
//					
//					if(new Random().nextInt(100) > 90){
//						try{
//							int r = new Random().nextInt(Main.HEIGHT - 3);
//							for(int i = 0; i <= r; i++){
//								tiles[x - i][y - i] = new CementFloor(x - i, y - i);
//							}
//						}catch(Exception e){}
//					}
				}
				
//				Alright, I'm gonna start on some random level generation
//				I'm doing pretty shit
//				yolo
//				One min, gotta add the game itself to obs
				if (new Random().nextInt(100) > 80){
					try{
						if(tiles[x][y] instanceof CementCore)
						tiles[x][y - 1] = new CementCore(x, y - 1);
					}catch(Exception e){}
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
	}
	
	public void tick(){
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		bg.draw(g);
		super.render(g, c);
	}
	
	
}
