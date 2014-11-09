package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.io.Debug;

public class BackgroundSheet {

	public boolean isStatic = false;
	private final BufferedImage sheet;
	private int width, height, enlargement;
	
	public BackgroundSheet(String path){
		BufferedImage temp = null;
		Debug.printDebug("Creating new BackgroundSheet: " + path);
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Failed to read " + path);
		}finally{
			sheet = temp;
		}
	}
	
	
	public BackgroundSheet(String path, int enlarge){
		
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(path));
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Failed to read " + path);
		}finally{		
			sheet = temp;
		}
		
		enlargement = enlarge;
		width = sheet.getWidth();
		height = sheet.getHeight();
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g, int x, int y){
		g.drawImage(sheet, x, y, null);
	}
	
	public void drawEnlarged(Graphics g, int x, int y){
//		g.drawImage(sheet, x, y, getWidth(), getHeight(), null);
		Main.getGame().getCamera().renderImageNoOff(g, sheet, x, y, enlargement);
	}
	
	public void draw(Graphics g, double x, double y){
//		g.drawImage(sheet, (int)x, (int)y, null);
		Main.getGame().getCamera().renderImageNoOff(g, sheet, (int)x, (int)y);
	}
	
	public BufferedImage getImage(){
		return sheet;
	}
	
	public int getWidth(){
		return width * enlargement;
	}
	
	public int getHeight(){
		return height * enlargement;
	}
}
