package com.hanfeldt.game.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.characters.NPCCharacter;
import com.hanfeldt.game.math.CircleHelper;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class Hud {
	public static boolean debug = false, muted = false, wepSelect = false;
	private static int hearts, bullets;
	private static int heartx = 0;
	private int switcherRad = 40;
	private Sprite heart, character, deadCharacter, pistol /*,ammo*/;
	private Player player;
	
	private boolean hasFocus = true;
	public static Color defWhite = new Color(255 , 255, 255);
	public static Color transDark = new Color(0, 0, 0, 80);
	public  ArrayList<InfoPopUp> popUpList;
	private Font font = new Font("Arial", Font.PLAIN, 9);
	private ImageIcon focusNagger1, focusNagger2;
	
	private WeaponSwitcher wSwitcher;
	
	public Hud(Player player){
		heart = new Sprite(SpriteSheet.getSheet(SpriteSheet.misc), 0, 0, 1, 1);
		character = new Sprite(SpriteSheet.getSheet(SpriteSheet.misc), 2, 0, 1, 1);
		deadCharacter = new Sprite(SpriteSheet.getSheet(SpriteSheet.misc), 1, 0, 1, 1);
		pistol = new Sprite(Main.getSpritesheet(), 0, 4, 1, 1);
//		ammo = new Sprite(Main.getSpritesheet(), 1, 4, 1, 1);
		this.player = player;
		try {
			focusNagger1 = new ImageIcon(ImageIO.read(Hud.class.getResource("/images/FocusNagger1.png")));
			focusNagger2 = new ImageIcon(ImageIO.read(Hud.class.getResource("/images/FocusNagger2.png")));
		}catch(Exception e) {
			e.printStackTrace();
		}
		popUpList = new ArrayList<>();
		wSwitcher = new WeaponSwitcher(new Point(0, 0), switcherRad);
	}
	
	public void tick(){
		hearts = player.getHealth() / 20;
		if(player.getWeaponEquipped() instanceof AmmoWeapon) {
			bullets = ((AmmoWeapon) player.getWeaponEquipped()).getAmmoInClip();
		}else{
			bullets = 0;
		}
		//Weapon select
		if(Main.getGame().getListener().qDown){
			if(!wepSelect){
				wSwitcher.position.setLocation(new Point(Main.mouseX, Main.mouseY));
				wSwitcher.circle = new CircleHelper(wSwitcher.position.x, wSwitcher.position.y, switcherRad);
				wSwitcher.wepSprites = player.inventory.weaponSprites;
				wepSelect = true;
			}
			wSwitcher.tick();
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
		g.drawString("SCORE: " + Long.toString(player.getScore()), Main.WIDTH /2 - 30, 8);
		
		for(int i = 1; i <= hearts; i++){
			heart.draw(g, heartx, Main.HEIGHT - 15);
			heartx += 15;
		}
		
		heartx = 19;
		
		//Weapon select
		if(wepSelect)
			wSwitcher.render(g);
		
		//stamina bar
		if (player.getStamina() < 20){
			g.setColor(new Color(0xfff60a0a));
		}else{
			g.setColor(new Color(0xff10e415));
		}
		g.fillRect(23, Main.HEIGHT - 23, player.getStamina()  , 6);
		
		if(player.getStamina() <= 0){
				g.fillRect(23, Main.HEIGHT - 23, 1, 6);
		}
		
		g.setColor(defWhite);
		
		if(debug){
			g.drawString("Debug Mode", 10, 10);
			g.drawString("Fps: " + Integer.toString(Main.fps), 10, 20);
			g.drawString("CHARACTER ENTITIY:", 10, 40);
			
			g.drawString(String.format("X: %d Y: %d Layer: %d", player.getX(), player.getY(), player.getLayer()), 10, 50); //Well Dayum, it can
			
			g.drawString(String.format("Entities: %d NPC: %d Bullets: %d", Main.getGame().entityManager.getEntities().toArray().length, Main.getGame().getNpc().toArray().length, Main.getGame().getBullets().size()), 10, 60);
			
			//A red cross wherever the game thinks the mouse is.
			g.setColor(Color.RED);
			g.drawLine(Main.mouseX -1, Main.mouseY -1, Main.mouseX +1, Main.mouseY +1);
			g.drawLine(Main.mouseX +1, Main.mouseY -1, Main.mouseX -1, Main.mouseY +1);
			
			//A blue cross for closest entity to player.
			g.setColor(Color.BLUE);
			Point p = player.getClosestEntity();
			Camera cam = Main.getGame().getCamera();
			p.setLocation(p.getX() - cam.getX(), p.getY() - cam.getY());
			g.drawLine(p.x -1, p.y -1, p.x +1, p.y +1);
			g.drawLine(p.x +1, p.y -1, p.x -1, p.y +1);
			
			//TODO temp, crosshair for entity closest to npc
			for(Entity npc: Main.getGame().getEntityManager().getEntities()){
				if(npc instanceof NPCCharacter){
					g.setColor(Color.WHITE);
					Point np = npc.getClosestEntity();
					np.setLocation(np.getX() - cam.getX(), np.getY() - cam.getY());
					g.drawLine(np.x -1, np.y -1, np.x +1, np.y +1);
					g.drawLine(np.x +1, np.y -1, np.x -1, np.y +1);
				}
			}
			
			g.dispose();
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
		
		for(InfoPopUp i : popUpList){
			if(i.showing){
				g.drawString(i.message, player.getX() - Main.getGame().getCamera().getX()- i.message.length(), player.getY() - Main.getGame().getCamera().getY());
				i.tick();
			}
		}
	}
	
	public void setHasFocus(boolean hf) {
		hasFocus = hf;
	}
	
	public Sprite getCharacterHead(){
		return character;
	}
	
}
