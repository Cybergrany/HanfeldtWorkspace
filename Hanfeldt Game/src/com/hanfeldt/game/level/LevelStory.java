package com.hanfeldt.game.level;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.AmmoPickup;
import com.hanfeldt.game.tile.Block;
import com.hanfeldt.game.tile.CementBack;
import com.hanfeldt.game.tile.CementCore;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.CementRoof;
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
		
		tiles = new TileArrayList<Tile>();
		tiles.addToInnerArray(sizeX, sizeY, null);
		
		spawner = new Spawner();
		
		generateLevel();
		
		
		setBg(level);
		
		player = p;
	}
	
	public void generateLevel(){
		for(int x=0; x<sizeY; x++) {
			for(int y=0; y<sizeX; y++) {
				switch(levelImage.getRGB(y, x)) {
				case 0xFF000000:
					tiles.addToInnerArray(y, x, new Block(y, x));
					break;
				case 0xff0000ff:
					tiles.addToInnerArray(y, x, new ZombieSpawner(y, x));
					for(int i2 = 0; i2 < Zombie.getMaxNpc(); i2++) {
						spawner.spawnNpc(new Zombie(Main.TILE_SIZE *y + (i2*30), Main.TILE_SIZE * x - 40));
					}
					break;
				case 0xff00FF00:
					tiles.addToInnerArray(y, x, new AmmoPickup(y, x));
					break;
				case 0xffFF0000:
					tiles.addToInnerArray(y, x, new CementCore(y, x));
					break;
				case 0xff00008C:
					tiles.addToInnerArray(y, x, new CementFloor(y, x));
					break;
				case 0xff007700:
					tiles.addToInnerArray(y, x, new CementRoof(y, x));
					break;
				case 0xffFFFF00:
					tiles.addToInnerArray(y, x, new RoofLamp(y, x));
					break;
				case 0xff9B0000:
					tiles.addToInnerArray(y, x, new CementBack(y, x));
					break;
				default:
					tiles.addToInnerArray(y, x, new Air(y, x));
				}
			}
		}
	}
	
	public void tick(){
		if(player.levelFinished){
			if(level +1 >= Main.getGame().getLevels().length) {
				//Win code
				Main.getGame().setState(new GameWon(Main.getGame()));
			}else{
				level++;
				Story.setLevel(level);
				setBg(level);
				player.setX(0);
				player.setY(Main.HEIGHT - Main.TILE_SIZE - player.getSizeY());
				player.levelFinished = false;
			}
		}
		
		bg.tick();
	}
	
}
