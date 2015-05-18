package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.io.ResourceManager;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Tile;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	protected Player player;
	public TileArrayList<Tile> tiles;
	public ArrayList<Layer> layers;
	public Point playerSpawn = new Point(Main.WIDTH / 2, Main.HEIGHT - Main.TILE_SIZE - Main.getGame().getPlayer().getSizeY());//Default
	
	public static int level = 0;
	protected int sizeX, sizeY;
	public BackgroundParallax bgp;
	public LayerMap layerMap;//TODO nullpointerexception can happen here, yano?
	private TileActionManager actionManager;
	protected BackgroundStatic[] bgs;
	
	public int layerAmount;
	
	public void render(Graphics g, Camera c) {
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
	 * Returns the ArrayList of tiles
	 * @return tiles
	 */
	public TileArrayList<Tile> getTile(){
		return tiles;
	}
	
	/**
	 * Add a tile to the game
	 * @param x
	 * @param y
	 * @param t
	 */
	public void setTile(int x, int y, Tile t) {
		tiles.addTile(x, y, t);
	}
	
	/**
	 * Initialize the background, and create the respective layers
	 */
	public void initBackgrounds(){
		bgp = new BackgroundParallax(level+1);
		boolean hasStatic = LevelLoader.hasStaticBg;
		if (hasStatic){
			layerAmount = LevelLoader.staticBgAmount;
			layers = new ArrayList<>();
			bgs = new BackgroundStatic[layerAmount];
			for(int i = 0; i < bgs.length; i++){
				bgs[i] = new BackgroundStatic(level+1, i);
			}
		}else{
			layerAmount = 0;
		}
	}
	
	/**
	 * Add a background to the specified layer.
	 * Please note that only one bg can be assigned per layer, and it is unreccomended to use parrallax backgrounds
	 * This is only called from backgroundStatic, and the layer itself is created here
	 */
	public void addBgToLayer(BackgroundStatic b){
		layers.add(new Layer(b));
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
		for(int i = 0; i < Main.getGame().getNpc().toArray().length; i++){
//			Main.getGame().getNpc().remove(i);
//			Main.getGame().getEntityManager().removeNpc(i);TODO Fix
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
//	public boolean spawnedFromSpawner(int x, int y){
//		if(tiles.getTile(x, y) instanceof ZombieSpawner){
//			for(int i = 0; i < Main.getGame().getNpc().size(); i++){
//				if(Main.getGame().getNpc().get(i).getSpawnLocation() == new Dimension(x, y)){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	public Point getPlayerSpawnPoint(){
		return playerSpawn;
	}
	
	public void setPlayerSpawn(int x, int y){
		playerSpawn.setLocation(x, y);
	}
	
	public void setPlayerSpawn(Point p){
		playerSpawn.setLocation(p);
	}
	
	/**
	 * Creates an index of the tile triggers in the level, which can then be used with config files to cause specific triggers to occur.
	 * TODO Enumerate action tiles instead of triggers
	 */
	public void addLevelTrigger(Tile t){
		LevelLoader.currentLevelTileTrigger++;
		if(Main.debug)
		ResourceManager.appendToFile(t.name + ", " + t.getX() + ", " + t.getY() ,
																String.format("/config/levels/level%d/",  Story.getCurrentLevel() + 1), "triggerBlocks.txt");//NOTE THAT THIS IS NOT NEEDED ONCE THE DESIGN OF THE LEVEL IS FINISHED - this is simply here as a reference to level designers
	}
	
	public int getDefaultLayer(int x, int y){
		try{
			return layerMap.getDefaultLayer(x, y);
		}catch(Exception e){
			System.err.println("Unable to get default layer. Possibly due to unitialised layerMap object in Level.java");
			e.printStackTrace();
			return 0;
		}
	}
	
}
