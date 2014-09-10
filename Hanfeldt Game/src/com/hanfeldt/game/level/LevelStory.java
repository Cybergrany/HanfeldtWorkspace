package com.hanfeldt.game.level;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.monsters.Zombie;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.AmmoPickup;
import com.hanfeldt.game.tile.Block;
import com.hanfeldt.game.tile.CementBack;
import com.hanfeldt.game.tile.CementCore;
import com.hanfeldt.game.tile.CementCoreModern;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.CementRoof;
import com.hanfeldt.game.tile.RoadBase;
import com.hanfeldt.game.tile.RoadSurface;
import com.hanfeldt.game.tile.RoofLamp;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class LevelStory extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private final BufferedImage levelImage;
	
	public LevelStory(String path, Player p) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			levelImage = temp;
		}
		
		sizeY = Main.HEIGHT /Main.SPRITE_SIZE;
		sizeX = levelImage.getWidth();
		
		tiles = new TileLinkedList<Tile>();
		tiles.addTile(sizeX, sizeY, null);
		
		generateLevel();
		
		player = p;
	}
	
	public void generateLevel(){
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				switch(levelImage.getRGB(x, y)) {
				case 0xFF000000:
					tiles.addTile(x, y, new Block(x, y));
					break;
				case 0xff0000ff:
					tiles.addTile(x, y, new ZombieSpawner(x, y));
					for(int i2 = 0; i2 < Zombie.getMaxNpc(); i2++) {
						Spawner.spawnNpc(new Zombie(Main.TILE_SIZE *x + (i2*30), Main.TILE_SIZE * y - 40));
					}
					break;
				case 0xff00FF00:
					tiles.addTile(x, y, new AmmoPickup(x, y));
					break;
				case 0xffFF0000:
					tiles.addTile(x, y, new CementCore(x, y));
					break;
				case 0xff00008C:
					tiles.addTile(x, y, new CementFloor(x, y));
					break;
				case 0xff007700:
					tiles.addTile(x, y, new CementRoof(x, y));
					break;
				case 0xffFFFF00:
					tiles.addTile(x, y, new RoofLamp(x, y));
					break;
				case 0xff9B0000:
					tiles.addTile(x, y, new CementBack(x, y));
					break;
				case 0xffB200FF:
					tiles.addTile(x, y, new RoadSurface(x, y));
					break;
				case 0xffB20084:
					tiles.addTile(x, y, new RoadBase(x, y));
					break;
				case 0xffFFFF8C:
					tiles.addTile(x, y, new RoadBase(x, y));//TODO Nothing here atm
					break;
				case 0xff559F74:
					tiles.addTile(x, y, new CementCoreModern(x, y));
					break;
				default:
					tiles.addTile(x, y, new Air(x, y));
				}
			}
		}
	}
	
}
