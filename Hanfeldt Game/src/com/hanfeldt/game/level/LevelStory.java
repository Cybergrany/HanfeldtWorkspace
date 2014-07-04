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
		
		
		sizeY = Main.HEIGHT /Main.spriteSize;
		sizeX = levelImage.getWidth();
		tiles = new Tile[sizeX][sizeY];
		spawner = new Spawner();
		
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				switch(levelImage.getRGB(j, i)) {
				case 0xFF000000:
					tiles[j][i] = new Block(j, i);
					break;
				case 0xff0000ff:
					tiles[j][i] = new ZombieSpawner(j, i);
					for(int i2 = 0; i2 < Zombie.getMaxNpc(); i2++) {
						//TODO: Regular spawning, not just on level creation.
						spawner.spawnNpc(new Zombie(Main.tileSize *j + (i2*30), Main.tileSize * i - 40));
					}
					break;
				case 0xff00FF00:
					tiles[j][i] = new AmmoPickup(j, i);
					break;
				case 0xffFF0000:
					tiles[j][i] = new CementCore(j, i);
					break;
				case 0xff00008C:
					tiles[j][i] = new CementFloor(j, i);
					break;
				case 0xff007700:
					tiles[j][i] = new CementRoof(j, i);
					break;
				case 0xffFFFF00:
					tiles[j][i] = new RoofLamp(j, i);
					break;
				case 0xff9B0000:
					tiles[j][i] = new CementBack(j, i);
					break;
				default:
					tiles[j][i] = new Air(j, i);
				}
			}
		}
		
		setBg(level);
		
		player = p;
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
				player.setY(Main.HEIGHT - Main.tileSize - player.getSizeY());
				player.levelFinished = false;
			}
		}
		
		bg.tick();
	}
	
}
