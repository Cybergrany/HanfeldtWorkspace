package com.hanfeldt.game.level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Firework;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.CementCore;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.CementRoof;
import com.hanfeldt.game.tile.RoofLamp;
import com.hanfeldt.game.tile.Shop;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class LevelArcade extends Level {
	
	public int difficulty = 1;
	public int lengthBeforeDiffInc = 100;
	private boolean spawnShop = false;
	private boolean victoryShown = true;
	
	public static ArrayList<Firework> fireworks;
	private Random rand;
	private long nextFireworkTick = 0;
	private int fireworksDrawn = 0;
	
	public LevelArcade(Player p) {
		sizeY = Main.HEIGHT / Main.TILE_SIZE;
		sizeX = Main.WIDTH / Main.TILE_SIZE * 2;//Twice the size of screen
		
		tiles = new TileLinkedList<Tile>();
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
		
		fireworks = new ArrayList<Firework>();
		rand = new Random();
		nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(30) +30;
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
				if(new Random().nextInt(100) > 70){
					try{
						for(int i = 0; i <= 2; i++)
						if(tiles.getFromInnerArray(x, y)instanceof CementCore && tiles.getFromInnerArray(x, y - 2) instanceof Air){
							tiles.addToInnerArray(x, y - 2, new ZombieSpawner(x, y - 2));
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
				for(int i2 = 0; i2 < new Random().nextInt(Zombie.getMaxNpc() + difficulty); i2++) {
					spawner.spawnNpc(new Zombie(Main.TILE_SIZE * x + (i2*30), Main.TILE_SIZE * y - 40));
				}
			}
		}
	}
	
	public void tick(){
		super.tick();
		generateLevelContinuous();
		
		if(player.getX() / Main.TILE_SIZE > lengthBeforeDiffInc){
			lengthBeforeDiffInc += lengthBeforeDiffInc;
			spawnShop = true;
			victoryShown = false;
			player.changeScore(500 * difficulty);
		}
	}
	
	
	public void render(Graphics g, Camera c) {
		super.render(g, c);
		if(!victoryShown){
			drawFireworks(g, 10);
			g.drawString("Level up", Main.WIDTH / 2, Main.HEIGHT / 2);
		}
	}
	
	public void generateLevelContinuous(){
		int prevSizeX = sizeX;
		if(player.getX() / Main.TILE_SIZE > sizeX / 2){
			prevSizeX = sizeX;
			sizeX += Main.WIDTH / Main.TILE_SIZE + 2;
		}
		
		//Generate a floor
		for(int y = 0; y < sizeY; y++){
			for(int x = prevSizeX; x < sizeX - 2; x++){
				tiles.addToInnerArray(x, y, new Air(x, y));//Makes sure that there is something in the list to prevent outofbounds
					
				if(y > sizeY - 2){
					if(new Random().nextInt(100) > 5 && !tileFilled(x, y)){
						try{
							tiles.addToInnerArray(x, y, new CementCore(x, y));
							
//							System.out.println("Core added at x: " + x + "y: " + y + "Cores added: "+ temp);
						}catch(Exception e){}
						
						if(new Random().nextInt(100) > 90){
							try{
								int r = new Random().nextInt(sizeY - 6);
								for(int i = 0; i <= r; i++){
									tiles.addToInnerArray(x, y - i, new CementFloor(x, y - i));
								}
							}catch(Exception e){}
						}
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
		
		//Generate Roof
		for(int y=0; y<sizeY; y++) {
			for(int x = prevSizeX; x < sizeX - 2; x++) {
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
		
		for(int y=0; y<sizeY; y++) {
			for(int x = prevSizeX; x < sizeX; x++) {
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
			for(int x = prevSizeX; x < sizeX - 2; x++) {
				if(new Random().nextInt(100) > 70 - difficulty){
					try{
						for(int i = 0; i <= 2; i++)
						if(tiles.getFromInnerArray(x, y)instanceof CementCore && tiles.getFromInnerArray(x, y - 2) instanceof Air){
							tiles.addToInnerArray(x, y - 2, new ZombieSpawner(x, y - 2));
						}
					}catch(Exception e){}
				}
			}
		}
		
//		Spawn the zombies
		for(int y = 0; y < sizeY; y++){
			for(int x = prevSizeX; x < sizeX - 2; x++){
				if(tiles.getFromInnerArray(x, y) instanceof ZombieSpawner)
				for(int i2 = 0; i2 < new Random().nextInt(Zombie.getMaxNpc() + difficulty); i2++) {
					if(!spawnedFromSpawner(x, y))
					spawner.spawnNpc(new Zombie(Main.TILE_SIZE * x + (i2*30), Main.TILE_SIZE * y - 40));
				}
			}
		}
		
		//Add the in-game shop
		for(int y = 0; y < sizeY; y++){
			for(int x = prevSizeX; x < sizeX - 2; x++){
				if(spawnShop)
					try{
					if(tiles.getFromInnerArray(x, y + 1) instanceof CementFloor && tiles.getFromInnerArray(x + 1, y) instanceof Air){
						tiles.addToInnerArray(x, y, new Shop(x, y));
						difficulty++;//Difficulty only advances if a shop spawns?
						spawnShop = false;
					}
				}catch(Exception e){}
			}
		}
		
		//Gotta save memory somehow, even if it barely works
		//TODO: Make better
		//TODO: Somehow force the player to continue in the same direction
		//TODO: Don't code lazily and actually add code to generate the level in the opposite direction
		//TODO: Fuck it, the first one was easier.
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX - 2; x++){
				if(x < player.getX() / Main.TILE_SIZE - ((Main.WIDTH / 2) / Main.TILE_SIZE) - 10){
					tiles.addToInnerArray(x, y, null);//Sets passed tiles to null in the array, which should save memory and fps, right?
					for(int i = 0; i < Main.getGame().getNpc().size(); i++){
						if(Main.getGame().getNpc().get(i).getX() / Main.TILE_SIZE < x){
							Main.getGame().getNpc().remove(i);//Kills npcs you've passed
						}
					}
				}
			}
		}
	}
	
	private void drawFireworks(Graphics g, int amount){
		
		if(Main.getGame().getTotalTicks() >= nextFireworkTick) {
			switch(rand.nextInt(3)) {
			case 0:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.RED));
				break;
			case 1:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.BLUE));
				break;
			case 2:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.ORANGE));
				break;
			}
			fireworksDrawn++;
			nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(16) +16;
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).tick();
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).render(g);
		}
		if(fireworksDrawn > amount){
			victoryShown = true;
			fireworksDrawn = 0;
		}
	}
	
}
