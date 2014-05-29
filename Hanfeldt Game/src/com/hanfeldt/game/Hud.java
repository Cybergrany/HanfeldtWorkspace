package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class Hud {
	public static boolean paused = false, debug = false, muted = false;
	private static int hearts, bullets;
	private static int bulletx = 19, heartx = 0;
	private Sprite heart, character, deadCharacter, crossHair, weapon, ammo;
	private Player player;
	
	private boolean hasFocus = true;
	private Color defWhite = new Color(255 , 255, 255);
	private Color transDark = new Color(0, 0, 0, 80);
	private Font font = new Font("Arial", Font.PLAIN, 9);
	private ImageIcon focusNagger1, focusNagger2;
	
	public Hud(Player player, Sprite character){
		heart = new Sprite(Main.spriteSheet, 3, 0, 1, 1);
		this.character = character;
		deadCharacter = new Sprite(Main.spriteSheet, 4, 0, 1, 1);
		crossHair = new Sprite(Main.spriteSheet, 5, 0, 1, 1);
		weapon = new Sprite(Main.getSpritesheet(), 0, 4, 1, 1);
		ammo = new Sprite(Main.getSpritesheet(), 1, 4, 1, 1);
		this.player = player;
		try {
			focusNagger1 = new ImageIcon(ImageIO.read(Hud.class.getResource("/images/FocusNagger1.png")));
			focusNagger2 = new ImageIcon(ImageIO.read(Hud.class.getResource("/images/FocusNagger2.png")));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tick(){
		hearts = player.getHealth() / 20;
		bullets = ((AmmoWeapon) player.getWeaponEquipped()).getAmmoInClip();
	}
	
	public void draw(Graphics g){
		/*
		 *TODO: Trying to feex pixelly text
		 */
//		if(g instanceof Graphics2D){
//			Graphics2D g2 = (Graphics2D)g;
//			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		}
		g.setFont(font);
		g.setColor(defWhite);
		
		if(player.getHealth() <= 0){
			deadCharacter.draw(g, 5, Main.sizeY - Main.tileSize);
		}else{
			character.draw(g, 5, Main.sizeY - Main.tileSize);
		}
		
		if(player.getWeaponEquipped() instanceof AmmoWeapon) {
			for(int i = 1; i <= bullets; i++){
				ammo.draw(g,Main.sizeX - bulletx, Main.sizeY - 19);
				bulletx+=15;
			}
			weapon.draw(g, Main.sizeX-Main.tileSize, Main.sizeY - Main.tileSize);
			String ammoString = Integer.toString(((AmmoWeapon) player.getWeaponEquipped()).getTotalAmmo());
			g.drawString("Ammo: " + ammoString, Main.sizeX - 80, Main.sizeY - 20);
		}
		g.drawString("Money: " + Integer.toString(player.getMoney()), Main.sizeX - 80, Main.sizeY - 40);
		
		bulletx = 28;
		
		for(int i = 1; i <= hearts; i++){
			heart.draw(g, heartx, Main.sizeY - 15);
			heartx += 15;
		}
		
		heartx = 19;
		
		g.drawOval(Main.mouseX - 7, Main.mouseY - 7, 14, 14);
		crossHair.draw(g, Main.mouseX - 7, Main.mouseY - 7);
		
		if(paused){
			g.drawString("Game is paused.", Main.sizeX / 8, Main.sizeY / 2);
			g.drawString("Press 'M' to mute.", Main.sizeX / 8, Main.sizeY / 2 + 20);
		}
		
		if(debug){
			g.drawString("Debug Mode", 10, 10);
			g.drawString("Fps: " + Integer.toString(Main.fps), 10, 20);
			g.drawString("CHARACTER ENTITIY:", 10, 40);
			
			g.drawString(String.format("X: %d Y: %d", player.getX(), player.getY()), 10, 50); //Well Dayum, it can
			
			g.drawString(String.format("Entities: NPC: %d Bullets: %d", Main.npc.toArray().length, Main.getGame().bullets.toArray().length), 10, 60);
			
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
		
		if(!hasFocus) {
			if(Main.getGame().getTotalTicks() % 60 > 30) {
				g.drawImage(focusNagger1.getImage(), Main.sizeX /4, Main.sizeY /3, null);
			}else{
				g.drawImage(focusNagger2.getImage(), Main.sizeX /4, Main.sizeY /3, null);
			}
		}
	}
	
	public void setHasFocus(boolean hf) {
		hasFocus = hf;
	}
	
}
