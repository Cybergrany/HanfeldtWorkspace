package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Hud {
	public static boolean paused = false, debug = false, muted = false;
	private static int hearts, heartx = 0;
	private Sprite heart, character, deadCharacter;
	private Player player;
	
	private Color defWhite = new Color(255 , 255, 255);
	private Color transDark = new Color(0, 0, 0, 80);
	
	public Hud(Player player){
		heart = new Sprite(Main.spriteSheet, 3, 0, 1, 1);
		character = new Sprite(Main.spriteSheet, 1, 3, 1, 1);
		deadCharacter = new Sprite(Main.spriteSheet, 4, 0, 1, 1);
		this.player = player;
	}
	
	public void tick(){
		hearts = player.getHealth() / 20;//Max 5 hearts
	}
	
	public void draw(Graphics g){
		Font font = new Font("Arial", Font.PLAIN, 9);
		g.setFont(font);
		g.setColor(defWhite);
		
		if(hearts == 0){
			deadCharacter.draw(g, 5, Main.sizeY - Main.tileSize);
		}else{
			character.draw(g, 5, Main.sizeY - Main.tileSize);
		}
		
		for(int i = 1; i <= hearts; i++){
			heart.draw(g, heartx, Main.sizeY - 15);
			heartx += 15;
		}
		
		heartx = 19;
		
		if(paused){
			g.drawString("Game is paused.", Main.sizeX / 8, Main.sizeY / 2);
			g.drawString("Press 'M' to mute.", Main.sizeX / 8, Main.sizeY / 2 + 20);
			g.drawString("I feex.", Main.sizeX / 8, Main.sizeY / 2 + 40);
		}
		
		if(debug){
			g.drawString("Debug Mode", 10, 10);
			g.drawString("Fps: " + Integer.toString(Main.fps), 10, 20);
			g.drawString("CHARACTER ENTITIY:", 10, 40);
			
			g.drawString(String.format("X: %d Y: %d", player.getX(), player.getY()), 10, 50);
			
			g.drawString(String.format("X: %d Y: %d", player.getX(), player.getY()), 10, 50); //Well Dayum, it can
			
		}
		
		if(Main.gameOver){
			g.setColor(transDark);
			g.fillRect(0, 0, Main.sizeX, Main.sizeY);
			g.setColor(defWhite);
			g.drawString("Game Over", Main.sizeX / 2 - 20, Main.sizeY / 2);
		}
		
		if(muted){
			g.drawString("Muted", Main.sizeX - 27, Main.sizeY - 5);
		}
	}
}
