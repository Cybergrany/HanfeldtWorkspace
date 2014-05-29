package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;

public class BackgroundSheet {

	private final BufferedImage sheet;
	
	public BackgroundSheet(String path){
		//Copypastad code 'ere, sozzy
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Failed to read " + path);
		}finally{
			sheet = temp;
		}
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g, int x, int y){
		g.drawImage(sheet, x, y, null);
	}
	
	public void draw(Graphics g, double x, double y){
		g.drawImage(sheet, (int)x, (int)y, null);
	}
}
