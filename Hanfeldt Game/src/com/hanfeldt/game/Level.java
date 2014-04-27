package com.hanfeldt.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	private final BufferedImage levelImage;
	private Player player;
	private int[][] tiles;
	private int sizeX;
	private Sprite block;
	private int level = 1;
	
	public Level(String path, Sprite block, Player p) {
		this.block = block;
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
		tiles = new int[sizeX][sizeY];
		
		for(int i=0; i<sizeY; i++) {
			for(int j=0; j<sizeX; j++) {
				switch(levelImage.getRGB(j, i)) {
				case 0xFF000000:
					tiles[j][i] = 1;
					break;
				}
			}
		}
		
		player = p;
	}
	
	public void tick(){
		//TODO: Level number can possibly update here, calling the level change in main.
		player.tick();
	}
	
	public void render(Graphics g) {
		draw(g, player.getX());
		player.draw(g);
	}
	
	public void draw(Graphics g, int posX) {
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles.length; j++) {
				switch(tiles[j][i]) {
				case 1:
					block.draw(g, (j * Main.tileSize) - posX, i * Main.tileSize);
					break;
				}
			}
		}
	}
	
	public int getLevel(){
		return level;
	}
	
}
