package com.hanfeldt.game.splash;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;

public class Background {
	private BufferedImage image;
	
	public Background(String path){
		try {
			image = ImageIO.read(Background.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(image, 0, 0, Main.sizeX, Main.sizeY, null);
	}
	
}
