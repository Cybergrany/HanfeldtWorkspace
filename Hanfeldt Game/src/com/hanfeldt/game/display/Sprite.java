package com.hanfeldt.game.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;

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
		this.image = image;
		width = image.getWidth() /Main.TILE_SIZE;
		height = image.getHeight() /Main.TILE_SIZE;
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
		g.drawImage(image, x, y, getWidth(), getHeight(), null);
	}
	
	public void draw(Graphics g, int x, int y, boolean direction) {
		if(direction) {
			g.drawImage(image, x, y, null);
		}else{
			g.drawImage(image, x + (Main.TILE_SIZE), y, - Main.TILE_SIZE * width, Main.TILE_SIZE *height, null);
		}
	}
	
	public void draw(Graphics g, int x, int y, boolean direction, int walkingAnim) {
		if(direction) {
			g.drawImage(walkingAnims[walkingAnim], x, y, null);
		}else{
			g.drawImage(walkingAnims[walkingAnim], x + (Main.TILE_SIZE), y, - Main.TILE_SIZE * width, Main.TILE_SIZE *height, null);
		}
	}
	
	public BufferedImage getWalkingImage(boolean dir, int currentCycle){
		BufferedImage ret = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		
		if(dir) {
			g.drawImage(walkingAnims[currentCycle], 0, 0, null);
		}else{
			g.drawImage(walkingAnims[currentCycle], 0 + (Main.TILE_SIZE), 0, - Main.TILE_SIZE * width, Main.TILE_SIZE *height, null);
		}
		g.dispose();
		return ret;
	}
	
	public int getWalkingAnimsLength() {
		if(walkingAnims != null)
		return walkingAnims.length;
		return 0;
	}
	
	public int getTileWidth() {
		return width;
	}
	
	public int getTileHeight() {
		return height;
	}
	
	public int getWidth() {
		return width *Main.TILE_SIZE;
	}
	
	public int getHeight() {
		return height *Main.TILE_SIZE;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public BufferedImage getImage(boolean direction) { 
		if(direction) {
			return getImage();
		}else{
			BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.drawImage(getImage(), getWidth(), 0, 0, getHeight(), 0, 0, getWidth(), getHeight(), null);
			g.dispose();
			return image;
		}
	}
	
}
