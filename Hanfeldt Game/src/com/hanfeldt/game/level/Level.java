package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Tile;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	protected Player player;
	public Tile[][] tiles;//Making this public static just to test things
	public static int level = 0;
	protected int sizeX, sizeY;
	protected Spawner spawner;
	protected Background bg;
	
	public void tick(){
		Main.getGame().getPlayer();
		if(player.levelFinished){
			if(level +1 >= Main.getGame().getLevels().length) {
				//Win code
				Main.getGame().setState(new GameWon(Main.getGame()));
			}else{
				level++;
				Story.setLevel(level);
				setBg(level);
				player.setX(0);
				player.setY(Main.HEIGHT - Main.TILE_SIZE *2 - player.getSizeY());
				player.levelFinished = false;
			}
		}
		player.tick();
		
		for(Npc n : Main.getGame().getNpc()) {
			n.tick();
		}
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		draw(g, c);
	}
	
	public void draw(Graphics g, Camera c) {
		bg.draw(g);
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles.length; j++) {
				c.renderTile(g, tiles[j][i]);
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
	
	/**
	 * Temporary method to clear the current level, avoiding conflicts 
	 * when switching between game modes.
	 */
	public void clearLevel(){
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				setTile(x, y, new Air(x, y));
			}
		}
		for(int i = 0; i < Main.getGame().npc.toArray().length; i++){
			Main.getGame().npc.remove(i);
		}
	}
	
}
