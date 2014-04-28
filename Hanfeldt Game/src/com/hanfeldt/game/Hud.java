package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Hud {
	
	private Sprite heart, character;
	
	public static boolean paused = false, debug = false;
	private static int hearts, heartx = 0;
	
	public Hud(){
		heart = new Sprite(Main.spriteSheet, 3, 0, 1, 1);
		character = new Sprite(Main.spriteSheet, 2, 1, 1 , 1);
	}
	
	public void tick(){
		hearts = Player.health / 20;//Max 5 hearts
	}
	
	public void draw(Graphics g){
		Font font = new Font("Arial", Font.PLAIN, 9);
		g.setFont(font);
		g.setColor(new Color(255 , 255, 255));
		
		character.draw(g, 5, Main.sizeY - 15);
		
		for(int i = 1; i <= hearts; i++){
			heart.draw(g, heartx, Main.sizeY - 15);
			heartx += 19;
		}
		
		heartx = 19;
		
		if(paused){
			g.drawString("Game is paused.", Main.sizeX / 8, Main.sizeY / 2);
			g.drawString("If shit is still happening, then something is wrong", Main.sizeX / 8, Main.sizeY / 2 + 20);
			g.drawString("I feex.", Main.sizeX / 8, Main.sizeY / 2 + 40);
		}
		
		if(debug){
			g.drawString("Debug Mode", 10, 10);
			g.drawString("Fps: " + Integer.toString(Main.fps), 10, 20);
			g.drawString("CHARACTER ENTITIY:", 10, 40);
			g.drawString("X: " + Player.x + "Y: " + Player.y, 10, 50);//Sorry, this is a bit messy, not sure if formatting similar to printf can be used in drawString.
		}
	}
}
