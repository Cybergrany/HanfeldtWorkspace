package com.hanfeldt.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {
	private BufferedImage image;
	private int width, height;
	
	public Sprite(SpriteSheet sheet, int x, int y, int w, int h) {
		image = sheet.getImage(x, y, w, h);
		width = w;
		height = h;
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
	
}
