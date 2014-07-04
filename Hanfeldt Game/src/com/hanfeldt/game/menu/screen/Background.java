package com.hanfeldt.game.menu.screen;

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
		g.drawImage(image, 0, 0, Main.WIDTH, Main.HEIGHT, null);
	}
	
}
