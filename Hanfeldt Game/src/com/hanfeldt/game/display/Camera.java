package com.hanfeldt.game.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.particles.Gore;
import com.hanfeldt.game.entity.particles.GoreSpawn;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.tile.Tile;

public class Camera {
	private int x, y;
	private Player player;
	private int shakingTicks, zoom;
	private Random rand = new Random();
	
	public Camera(int x, int y, Player p) {
		this.x = x;
		this.y = y;
		this.player = p;
		zoom = 100;
	}
	
	public void renderSprite(Graphics g, Sprite s, int xOff, int yOff) {
		int screenX = xOff - x;
		int screenY = yOff - y;
		if(screenX + s.getWidth() >= 0 &&
			screenX <= Main.WIDTH &&
			screenY + s.getHeight() >= 0 &&
			screenY <= Main.HEIGHT) {
				s.draw(g, screenX, screenY, zoom);
		}
	}
	
	public void renderSprite(Graphics g, Sprite s, int xOff, int yOff, int enlargement) {
		int screenX = xOff - x;
		int screenY = yOff - y;
		if(screenX + s.getWidth() >= 0 &&
			screenX <= Main.WIDTH &&
			screenY + s.getHeight() >= 0 &&
			screenY <= Main.HEIGHT) {
				s.draw(g, screenX, screenY, enlargement);
		}
	}
	
	public void tick() {
		
		x = player.getX() - Main.WIDTH /2 + (Main.TILE_SIZE /2);
		y = (int) ((player.getY() - (Main.HEIGHT / 2 + (Main.TILE_SIZE/2) -10)));
		
		if(shakingTicks > 0) {
			x += rand.nextInt(5) -2;
			y += rand.nextInt(3) -1;
			shakingTicks--;
		}
	}
	
	public void renderEntityItem(Graphics g, EntityItem e){
		if(e.getDirection()){
			renderSprite(g, e.getReverseSprite(), e.getX(), e.getY());
		}else{
			renderSprite(g, e.getSprite(), e.getX(), e.getY());
		}
	}
	
	public void renderEntityLiving(Graphics g, EntityLiving e) {
		if(e instanceof Npc && e.getSprite().getWalkingAnimsLength() > 1){
			renderImage(g, e.getWalkingImage(), e.getX(), e.getY());
		}else if(e.getDirection()) {
				renderSprite(g, e.getReverseSprite(), e.getX(), e.getY());
			}else{
				renderSprite(g, e.getSprite(), e.getX(), e.getY());
			}

//		camera.renderImage(g, npc.get(i).getWalkingImage(), npc.get(i).getX(),  npc.get(i).getY());
	}
	
	public void renderTile(Graphics g, Tile t) {
		renderSprite(g, t.getSprite(), t.getX(), t.getY());
	}
	
	public void renderBullet(Graphics g, Bullet b) {
		int screenX = b.getX() - x;
		int screenY = b.getY() - y;
		if(screenX +1 >= 0 &&
			screenX <= Main.WIDTH &&
			screenY +1 >= 0 &&
			screenY <= Main.HEIGHT) {
			g.setColor(Bullet.COLOR);
			g.fillRect(screenX, screenY, 1, 1);
		}
	}
	
	public void renderGore(Graphics g, GoreSpawn gs){
			for(Gore gore : gs.gore){
				int screenX = gore.getX() - x;
				int screenY = gore.getY() - y;
				if(screenX + 1 >= 0 &&
					screenX <= Main.WIDTH &&
					screenY + 1 >= 0 &&
					screenY <= Main.HEIGHT){
					g.setColor(Color.RED);
					g.fillRect(screenX, screenY, 1, 1);
			}else{gs.removeGore();}
		}
	}
	
	public void renderBullet(Graphics g, Bullet b, Sprite s){
		renderSprite(g, s, b.getX(), b.getY());
	}
	
	public void renderImage(Graphics g, BufferedImage image, int x, int y) {
		renderSprite(g, new Sprite(image), x, y);
	}
	
	public void renderImage(Graphics g, BufferedImage image, int x, int y, int enlargement) {
		renderSprite(g, new Sprite(image), x, y, enlargement);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void changeX(int c) {
		x += c;
	}
	
	public void changeY(int c) {
		x += c;
	}
	
	public void addShakingTicks(int a) {
		shakingTicks += a;
	}
	
	public void shake() {
		addShakingTicks(rand.nextInt(10) +20);
	}
	
}
