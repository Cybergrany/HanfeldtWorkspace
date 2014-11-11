package com.hanfeldt.game.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class InfoPopUp {
	
	private Graphics gr;
	private String message;
	public boolean triggered = false;
	
	private int x, y;
	
	public InfoPopUp(String text, int x, int y, int size){
		gr = Main.getGame().getPanel().getGraphics();
		this.x = x;
		this.y = y;
		message = text;
		gr.setColor(Color.WHITE);
		gr.setFont(new Font("Kai", Font.PLAIN, size));
	}
	
	public InfoPopUp(String text, int x, int y, int size, int style, Color c){
		this(text, x, y, size);
		gr.setColor(c);
		gr.setFont(new Font("Kai", style, size));
	}
	
	public InfoPopUp(Graphics g, String text, int x, int y, int size){
		this(text, x, y, size);
		gr = g;
	}
	
	public void draw(){
		gr.drawString(message, x * Main.TILE_SIZE, y * Main.TILE_SIZE);
		if(Main.getGame().getListener().eDown){
			triggered = true;
		}
	}
}
