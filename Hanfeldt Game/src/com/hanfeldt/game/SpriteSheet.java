package com.hanfeldt.game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private final BufferedImage sheet;
	
	public SpriteSheet(String filePath) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(filePath));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			sheet = temp;
		}
	}
	
	public BufferedImage getImage(int row, int col, int width, int height) {
		return sheet.getSubimage(row * Main.spriteSize, col * Main.spriteSize, width * Main.spriteSize, height * Main.spriteSize);
	}
	
}
