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
import com.hanfeldt.game.tile.RoofLamp;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class LevelArcade extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	public LevelArcade(Player p) {
		sizeY = Main.HEIGHT / Main.TILE_SIZE;
		sizeX = Main.WIDTH / Main.TILE_SIZE * 2;//Twice the size of screen
		
		//*Sigh* I'm not too good when it comes to this kinda stuff, but let's see if this works
		tiles = new TileArrayList<Tile>();
		tiles.addToInnerArray(sizeX, sizeY, null);
		
		spawner = new Spawner();
		
		//Set all blocks to air
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				tiles.addToInnerArray(j, i, new Air(j, i));
			}
		}
		
		generateLevel(/*p*/);
		
		setBg(level);
		
		player = p;
	}
	
	public void generateLevel(/*Player p*/){
		
		//Rough Terrain
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(y > sizeY - 2){
					
					//Floor (With gaps)
					if(new Random().nextInt(100) > 10){
						try{
							tiles.addToInnerArray(x, y, new CementCore(x, y));
						}catch(Exception e){}
					}
					
					
					
					if(new Random().nextInt(100) > 90){
						try{
							int r = new Random().nextInt(sizeY - 6);
							for(int i = 0; i <= r; i++){
								tiles.addToInnerArray(x, y - i, new CementFloor(x, y - i));
							}
						}catch(Exception e){}
					}
				}
				
				
				if (new Random().nextInt(100) > 80){
					try{
						if(tiles.getFromInnerArray(x, y) instanceof CementCore)
							tiles.addToInnerArray(x, y - 1, new CementCore(x, y - 1));
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
							tiles.addToInnerArray(x, y, new CementRoof(x, y));
							//Lamps
							if(new Random().nextInt(100) > 90){
								tiles.addToInnerArray(x, y + 1, new RoofLamp(x, y + 1));
							}
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
					if(tiles.getFromInnerArray(x, y + 1) instanceof CementCore && tiles.getFromInnerArray(x, y) instanceof Air){
						tiles.addToInnerArray(x, y + 1, new CementFloor(x, y + 1));
					}
				}catch(Exception e){}
				//Left-hand side wall
				if(x < 1){
					tiles.addToInnerArray(x, y, new CementCore(x, y));
				}
			}
		}
		
		//Spawners
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(new Random().nextInt(100) > 80){
					try{
						for(int i = 0; i <= 2; i++)
						if(tiles.getFromInnerArray(x, y)instanceof CementCore && tiles.getFromInnerArray(x, y) instanceof Air){
							tiles.addToInnerArray(x, y, new ZombieSpawner(x, y));
						}
					}catch(Exception e){}
				}
			}
		}
		
		//TODO:Ensure player won't spawn stuck in block or over hole
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				//Screw it; I'll do this later
				//Player doesn't seem to spawn inside a block anyways
			}
		}
		
		//Spawn Zombies
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				if(tiles.getFromInnerArray(x, y) instanceof ZombieSpawner)
				for(int i2 = 0; i2 < new Random().nextInt(Zombie.getMaxNpc() + 1); i2++) {
					spawner.spawnNpc(new Zombie(Main.TILE_SIZE *x + (i2*30), Main.TILE_SIZE * y - 40));
				}
			}
		}
	}
	
	public void tick(){
		bg.tick();
		generateLevelContinuous();
	}
	
	public void render(Graphics g, Camera c) {
		bg.draw(g);
		super.render(g, c);
	}
	
	public void generateLevelContinuous(){
		int prevSizeX = sizeX;
		if(player.getX() / Main.TILE_SIZE > sizeX / 2){
			prevSizeX = sizeX;
			System.err.println(prevSizeX);
			sizeX += Main.WIDTH / Main.TILE_SIZE + 2;
			System.out.println(sizeX);
		}
		
		for(int y = 0; y < sizeY; y++){
			for(int x = prevSizeX - 2; x < sizeX; x++){
//				tiles[x][y] = new Air(x, y);
//				System.out.println("Air placed at x: " + x + "y: " + y);
				
//				if(y > sizeY - 2){
//					System.out.println("More floor generated at x: " + x + "y: " + y);
//				}
			}
			
		}
//		System.out.println(player.getX() / Main.TILE_SIZE);
	}
	
	
}
