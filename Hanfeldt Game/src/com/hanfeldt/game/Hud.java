package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class Hud {
	public static boolean debug = false, muted = false;
	private static int hearts, bullets;
	private static int heartx = 0;
	private Sprite heart, character, deadCharacter, pistol /*,ammo*/;
	private Player player;
	
	private boolean hasFocus = true;
	public static Color defWhite = new Color(255 , 255, 255);
	public static Color transDark = new Color(0, 0, 0, 80);
	private Font font = new Font("Arial", Font.PLAIN, 9);
	private ImageIcon focusNagger1, focusNagger2;
	
	public Hud(Player player, Sprite character){
		heart = new Sprite(Main.spriteSheet, 3, 0, 1, 1);
		this.character = character;
		deadCharacter = new Sprite(Main.spriteSheet, 4, 0, 1, 1);
		pistol = new Sprite(Main.getSpritesheet(), 0, 4, 1, 1);
//		ammo = new Sprite(Main.getSpritesheet(), 1, 4, 1, 1);
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
		if(player.getWeaponEquipped() instanceof AmmoWeapon) {
			bullets = ((AmmoWeapon) player.getWeaponEquipped()).getAmmoInClip();
		}else{
			bullets = 0;
		}
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
			deadCharacter.draw(g, 5, Main.HEIGHT - Main.TILE_SIZE);
		}else{
			character.draw(g, 5, Main.HEIGHT - Main.TILE_SIZE);
		}
		
		if(player.getWeaponEquipped() instanceof AmmoWeapon) {
//			for(int i = 1; i <= bullets; i++){
//				ammo.draw(g,Main.sizeX - bulletx, Main.sizeY - 19);
//				bulletx+=15;
//			}
			pistol.draw(g, Main.WIDTH-Main.TILE_SIZE, Main.HEIGHT - Main.TILE_SIZE);
			String ammoString = Integer.toString(((AmmoWeapon) player.getWeaponEquipped()).getTotalAmmo());
			String bulletString = Integer.toString(bullets);
			
			
			g.drawString(bulletString + "|" + ammoString, Main.WIDTH - 28, Main.HEIGHT - 5);
		}
		g.drawString("$" + Integer.toString(player.getMoney()), Main.WIDTH - 30, Main.HEIGHT - 15);
		
		for(int i = 1; i <= hearts; i++){
			heart.draw(g, heartx, Main.HEIGHT - 15);
			heartx += 15;
		}
		
		heartx = 19;
		
		if(debug){
			g.drawString("Debug Mode", 10, 10);
			g.drawString("Fps: " + Integer.toString(Main.fps), 10, 20);
			g.drawString("CHARACTER ENTITIY:", 10, 40);
			
			g.drawString(String.format("X: %d Y: %d", player.getX(), player.getY()), 10, 50); //Well Dayum, it can
			
			g.drawString(String.format("Entities: NPC: %d Bullets: %d", Main.getGame().getNpc().toArray().length, Main.getGame().bullets.toArray().length), 10, 60);
			
		}
		
		if(muted){
			g.drawString("Muted", Main.WIDTH - 60, Main.HEIGHT - 5);
		}
		
		if(!hasFocus) {
			if(Main.getGame().getTotalTicks() % 60 > 30) {
				g.drawImage(focusNagger1.getImage(), Main.WIDTH /4, Main.HEIGHT /3, null);
			}else{
				g.drawImage(focusNagger2.getImage(), Main.WIDTH /4, Main.HEIGHT /3, null);
			}
		}
	}
	
	public void setHasFocus(boolean hf) {
		hasFocus = hf;
	}
	
}
