package com.hanfeldt.game.level;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	protected Player player;
	public TileLinkedList<Tile> tiles;
	public Point playerSpawn = new Point(Main.WIDTH / 2, Main.HEIGHT - Main.TILE_SIZE - Main.getGame().getPlayer().getSizeY());//Default
	
	public static int level = 0;
	protected int sizeX, sizeY;
	protected Background bg;
	
	public void tick(){
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		draw(g, c);
	}
	
	public void draw(Graphics g, Camera c) {
		bg.draw(g);
		for(int i=0; i<tiles.getTileArraySize(); i++) {
			for(int j=0; j<tiles.size(); j++) {
				try{
				if(tiles.getTile(j, i) != null && tiles.getTile(j, i).isVisible())
				c.renderTile(g, tiles.getTile(j, i));
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
	
	/**
	 * Returns the linkedList of tiles
	 * @return tiles
	 */
	public TileLinkedList<Tile> getTile(){
		return tiles;
	}
	
	public void setTile(int x, int y, Tile t) {
		tiles.addTile(x, y, t);
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
	
	/**
	 * Returns true if a space is occupied by a tile
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean tileFilled(int x, int y){
		if(tiles.getTile(x, y) instanceof Tile && !(tiles.getTile(x, y) instanceof Air)){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if a zombie has been spawned from this spawner
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean spawnedFromSpawner(int x, int y){
		if(tiles.getTile(x, y) instanceof ZombieSpawner){
			for(int i = 0; i < Main.getGame().getNpc().size(); i++){
				if(Main.getGame().getNpc().get(i).getSpawnLocation() == new Dimension(x, y)){
					return true;
				}
			}
		}
		return false;
	}
	
	public Point getPlayerSpawnPoint(){
		return playerSpawn;
	}
	
	public void setPlayerSpawn(int x, int y){
		playerSpawn.setLocation(x, y);
	}
	
	public void setPlayerSpawn(Point p){
		playerSpawn.setLocation(p);
	}
	
}
