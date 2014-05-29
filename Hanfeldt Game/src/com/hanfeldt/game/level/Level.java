package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.AmmoPickup;
import com.hanfeldt.game.tile.Block;
import com.hanfeldt.game.tile.Tile;
import com.hanfeldt.game.tile.ZombieSpawner;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private final BufferedImage levelImage;
	private Player player;
	public Tile[][] tiles;//Making this public static just to test things
	public static int level = 0;
	private int sizeX, sizeY;
	private Spawner spawner;
	private Background bg;
	
	public Level(String path, Player p) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			levelImage = temp;
		}
		
		
		sizeY = Main.sizeY / 16;
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
				default:
					tiles[j][i] = new Air(j, i);
				}
			}
		}
		
		setBg(level);
		
		player = p;
	}
	
	public void tick(){
		Main.getGame().getPlayer();
		if(player.levelFinished){
			level++;
			Main.setLevel(level);
			setBg(level);
			player.setX(0);
			player.setY(48);
			player.levelFinished = false;
		}
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
				tiles[j][i].getSprite().draw(g, (j * Main.tileSize) - posX + (Main.sizeX /2) - (Main.tileSize /2), i * Main.tileSize);
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
