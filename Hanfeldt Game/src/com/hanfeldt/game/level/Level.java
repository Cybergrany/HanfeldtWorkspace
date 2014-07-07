package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Tile;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	protected Player player;
//	public Tile[][] tiles;//Making this public static just to test things
	public TileArrayList<Tile> tiles;
	
	
	public static int level = 0;
	protected int sizeX, sizeY;
	protected Spawner spawner;
	protected Background bg;
	
	public void tick(){
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		draw(g, c);
	}
	
	public void draw(Graphics g, Camera c) {
		bg.draw(g);
		for(int i=0; i<tiles.getInnerArraySize(); i++) {
			for(int j=0; j<tiles.size(); j++) {
				try{
				if(tiles.getFromInnerArray(j, i) != null)
				c.renderTile(g, tiles.getFromInnerArray(j, i));
				}catch(Exception e){}
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
		return tiles.get(x).get(y);
	}
	
	public void setTile(int x, int y, Tile t) {
		tiles.addToInnerArray(x, y, t);
	}
	
	public void setBg(int currentLevel){
		bg = new Background(currentLevel);
	}
	
	/**
	 * Temporary method to clear the current level, avoiding conflicts 
	 * when switching between game modes.
	 * TODO:Feex
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
