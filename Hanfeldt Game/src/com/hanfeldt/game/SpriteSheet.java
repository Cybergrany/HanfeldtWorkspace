package com.hanfeldt.game;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private static final int tileHeight = 16;
	private final BufferedImage sheet;
	
	public SpriteSheet(String filePath) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(new File(filePath));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			sheet = temp;
		}
	}
	
	public BufferedImage getImage(int row, int col, int width, int height) {
		return sheet.getSubimage(row * tileHeight, col * tileHeight, width * tileHeight, height * tileHeight);
	}
	
}
