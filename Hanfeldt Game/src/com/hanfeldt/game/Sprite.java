package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {
	private BufferedImage image;
	private BufferedImage[] walkingAnims;
	private int width, height;
	
	public Sprite(SpriteSheet sheet, int x, int y, int w, int h) {
		image = sheet.getImage(x, y, w, h);
		width = w;
		height = h;
	}
	
	public Sprite(BufferedImage image) {
		width = image.getWidth() /Main.tileSize;
		height = image.getHeight() /Main.tileSize;
	}
	
	public Sprite(SpriteSheet sheet, int x, int y, int w, int h, int numWalkingAnims) {
		image = sheet.getImage(x, y, w, h);
		width = w;
		height = h;
		walkingAnims = new BufferedImage[numWalkingAnims];
		
		for(int i=0; i<numWalkingAnims; i++) {
			walkingAnims[i] = sheet.getImage(x + i +1, y, w, h);
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, Main.tileSize, Main.tileSize, null);
	}
	
	public void draw(Graphics g, int x, int y, boolean direction) {
		if(direction) {
			g.drawImage(image, x, y, null);
		}else{
			g.drawImage(image, x + (Main.tileSize), y, - Main.tileSize * width, Main.tileSize *height, null);
		}
	}
	
	public void draw(Graphics g, int x, int y, boolean direction, int walkingAnim) {
		if(direction) {
			g.drawImage(walkingAnims[walkingAnim], x, y, null);
		}else{
			g.drawImage(walkingAnims[walkingAnim], x + (Main.tileSize), y, - Main.tileSize * width, Main.tileSize *height, null);
		}
	}
	
	public BufferedImage getWalkingImage(boolean dir, int currentCycle) {
		BufferedImage ret = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = ret.getGraphics();
		
		if(dir) {
			g.drawImage(walkingAnims[currentCycle], 0, 0, null);
		}else{
			g.drawImage(walkingAnims[currentCycle], 0 + (Main.tileSize), 0, - Main.tileSize * width, Main.tileSize *height, null);
		}
		g.setColor(Color.RED);
		g.fillRect(0, 0, ret.getWidth(), ret.getHeight());
		return ret;
	}
	
	public int getWalkingAnimsLength() {
		return walkingAnims.length;
	}
	
	public int getTileWidth() {
		return width;
	}
	
	public int getTileHeight() {
		return height;
	}
	
	public int getWidth() {
		return width *Main.tileSize;
	}
	
	public int getHeight() {
		return height *Main.tileSize;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	
}
