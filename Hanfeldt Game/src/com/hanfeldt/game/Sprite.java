package com.hanfeldt.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {
	private BufferedImage image;
	private BufferedImage[] walkingAnims;
	private int width, height;
	private int walkingSpeed;
	
	public Sprite(SpriteSheet sheet, int x, int y, int w, int h) {
		image = sheet.getImage(x, y, w, h);
		width = w;
		height = h;
	}
	
	public Sprite(SpriteSheet sheet, int x, int y, int w, int h, int numWalkingAnims) {
		image = sheet.getImage(x, y, w, h);
		width = w;
		height = h;
		
		if(numWalkingAnims > 0) {
			walkingAnims = new BufferedImage[numWalkingAnims];
		}
		
		for(int i=0; i<numWalkingAnims; i++) {
			walkingAnims[i] = sheet.getImage(x + i, y, w, h);
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, null);
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
	
}
