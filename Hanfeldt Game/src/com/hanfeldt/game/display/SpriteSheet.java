package com.hanfeldt.game.display;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;

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
		return sheet.getSubimage(row * Main.SPRITE_SIZE, col * Main.SPRITE_SIZE, width * Main.SPRITE_SIZE, height * Main.SPRITE_SIZE);
	}
	
}
