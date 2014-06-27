package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.CementFloor;
import com.hanfeldt.game.tile.Tile;

public class LevelArcade {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private Player player;
	public Tile[][] tiles;//Making this public static just to test things
	public static int level = 0;
	private int sizeX, sizeY;
	private Spawner spawner;
	private Background bg;
	
	public LevelArcade(Player p) {
		
		
		sizeY = Main.sizeY / 16;
		sizeX = Main.sizeX / 16;
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
	
	public void draw(Graphics g, int posX) {
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles.length; j++) {
				int screenX = (j * Main.tileSize) - posX + (Main.sizeX /2) - (Main.tileSize /2);
				if(screenX + Main.tileSize > 0 && screenX < Main.sizeX) {
					tiles[j][i].getSprite().draw(g, screenX, i * Main.tileSize);
				}
			}
		}
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
	}
	
	public void setBg(int currentLevel){
		bg = new Background(currentLevel);
	}
	
}
