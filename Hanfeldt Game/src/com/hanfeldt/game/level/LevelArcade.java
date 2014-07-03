package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.Tile;

public class LevelArcade extends Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	public LevelArcade(Player p) {
		sizeY = Main.sizeY / Main.tileSize;
		sizeX = Main.sizeX / Main.tileSize;
		tiles = new Tile[sizeX][sizeY];
		spawner = new Spawner();
		
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				tiles[j][i] = new Air(j, i);	
			}
		}
		
		generateLevel();
		
		setBg(level);
		
		player = p;
	}
	
	public void generateLevel(){
		for(int y=0; y<sizeY; y++) {
			for(int x=0; x<sizeX; x++) {
				if(y > sizeY - 2){
					try{
						tiles[x][y] = new CementFloor(x, y);
					}catch(Exception e){}
				}
			}
		}
	}
	
	public void tick(){
		Main.getGame().getPlayer();
//		if(player.levelFinished){
//			if(level +1 >= Main.getLevels().length) {
//				//Win code
//				Main.getGame().setState(new GameWon(Main.getGame()));
//			}else{
//				level++;
//				Main.setLevel(level);
//				setBg(level);
//				player.setX(0);
//				player.setY(Main.sizeY - Main.tileSize - player.getSizeY());
//				player.levelFinished = false;
//			}
//		}
		player.tick();
		
		for(int i=0; i<Main.npc.size(); i++) {
			Main.npc.get(i).tick();
		}
		bg.tick();
	}
	
	public void render(Graphics g) {
		bg.draw(g);
		
		draw(g, player.getX());
		
		for(int i=0; i<Main.npc.size(); i++) {
			Main.npc.get(i).draw(g);
		}
		
		if(player.alive) {
			player.draw(g);
		}
	}
	
	
}
