package com.hanfeldt.game.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.monsters.Zombie;
import com.hanfeldt.game.io.Debug;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Tile;

public class LevelStory extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private final BufferedImage levelImage;
	private final ArrayList<String[]> blocks;
	
	public LevelStory(String path, Player p) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			System.err.println("Woah your map is missing! Enjoy your newly crashed game :)");
			e.printStackTrace();
		}finally{
			levelImage = temp;
		}
		
//		sizeY = Main.HEIGHT /Main.SPRITE_SIZE;
		sizeY = levelImage.getHeight();
		sizeX = levelImage.getWidth();
		
		tiles = new TileLinkedList<Tile>();
		tiles.addTile(sizeX, sizeY, null);
		blocks = Main.getGame().blocks;
		
		generateLevel();
		player = p;
	}
	
	public void generateLevel(){
		ArrayList<String> colors = getUsedColorCodes();
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				colorLoop:
				for(int i = 0; i < colors.size(); i++){
					Color c = Color.decode(colors.get(i));
					if((colors.get(i) == blocks.get(i)[0]) && (levelImage.getRGB(x, y) == c.getRGB())){
						tiles.addTile(x, y, getTileFromID(blocks.get(i), x, y));
						break colorLoop;
					}else{
						tiles.addTile(x, y, new Air(x, y));
					}
				}
			}
		}
	}
	
	public ArrayList<String> getUsedColorCodes(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < blocks.size(); i ++){
			list.add(blocks.get(i)[0]);//[0] is the location of the Color code
		}
		return list;
	}
	
	public Tile getTileFromID(String[] id, int x, int y){
		int[] idi = new int[id.length];

		for (int i = 0; i < id.length; i++) {
		    try {
		        idi[i] = Integer.parseInt(id[i]);
		    } catch (NumberFormatException nfe) {};
		}
		Tile tile = new Tile(x, y,  idi[1], idi[2], idi[3], idi[4]);
		tile.isSolid = Boolean.parseBoolean(id[5]);
		tile.isVisible = Boolean.parseBoolean(id[6]);
		tile.name = id[7];
		tileAction(tile.name, x, y);
		return tile;
	}
	
	private void tileAction(String tileName, int x, int y){
		switch(tileName){
			case "ZombieSpawner":
				for(int i2 = 0; i2 < Zombie.getMaxNpc(); i2++) {
					Zombie zombie = new Zombie(Main.TILE_SIZE *x + (i2*30), Main.TILE_SIZE * y - 40);//Final tilesize used to be 40
					Spawner.spawnNpc(zombie);
				}
				break;
			case "playerSpawner":
				setPlayerSpawn(Main.TILE_SIZE*x, Main.TILE_SIZE * y);
				Debug.printDebug("Player spawn set to: " + Main.TILE_SIZE*x +  ", " + Main.TILE_SIZE * y);
				Main.getGame().getPlayer().setLocation(getPlayerSpawnPoint());
				break;
			default:
				return;
		}
	}
	
}
