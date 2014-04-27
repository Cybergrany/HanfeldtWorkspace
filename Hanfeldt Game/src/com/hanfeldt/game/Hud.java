package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Hud {
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		Font font = new Font("Arial", Font.PLAIN, 9);
		g.setFont(font);
		g.setColor(new Color(255 , 255, 255));
		
		g.drawString("Fps: " + Integer.toString(Main.fps), 10, 10);
		
		if(Main.isPaused){
			g.drawString("Game is paused.", Main.sizeX / 8, Main.sizeY / 2);
			g.drawString("If shit is still happening, then something is wrong", Main.sizeX / 8, Main.sizeY / 2 + 20);
			g.drawString("I feex.", Main.sizeX / 8, Main.sizeY / 2 + 40);
		}
	}
}
