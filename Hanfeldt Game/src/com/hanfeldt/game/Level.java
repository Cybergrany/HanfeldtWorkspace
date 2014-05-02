package com.hanfeldt.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.hanfeldt.game.npc.Npc;
import com.hanfeldt.game.tile.Air;
import com.hanfeldt.game.tile.Block;
import com.hanfeldt.game.tile.Tile;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private final BufferedImage levelImage;
	private Player player;
	public static Tile[][] tiles;//Making this public static just to test things
	private int sizeX;
	
	public Level(String path, Player p) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(new File(path));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			levelImage = temp;
		}
		
		int sizeY = Main.sizeY / 16;
		sizeX = levelImage.getWidth();
		tiles = new Tile[sizeX][sizeY];
		
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				switch(levelImage.getRGB(j, i)) {
				case 0xFF000000:
					tiles[j][i] = new Block(j, i);
					break;
				default:
					tiles[j][i] = new Air(j, i);
				}
			}
		}
		
		player = p;
	}
	
	public void tick(){
		player.tick();
		
		for(int i=0; i<Main.npc.size(); i++) {
			Main.npc.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		draw(g, player.getX());
		for(int i=0; i<Main.npc.size(); i++) {
			Main.npc.get(i).draw(g);
		}
		player.draw(g);
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
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
}
