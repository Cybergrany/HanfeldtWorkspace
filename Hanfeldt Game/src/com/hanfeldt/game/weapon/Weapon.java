	package com.hanfeldt.game.weapon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;

public abstract class Weapon {
	protected Sprite sprite;
	protected EntityLiving entity;
	protected long totalTicks = 0;
	public boolean automatic = true;
	
	public Weapon(EntityLiving e, Sprite s) {
		entity = e;
		sprite = s;
	}
	
	public void tick() {
		totalTicks++;
		if(totalTicks == Long.MAX_VALUE) {
			totalTicks = Long.MAX_VALUE;
		}
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Sprite getReverseSprite() {
		BufferedImage reverseImage = new BufferedImage(Main.TILE_SIZE, Main.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = reverseImage.getGraphics();
		g.drawImage(getSprite().getImage(), Main.TILE_SIZE, 0, 0, Main.TILE_SIZE, 0, 0, Main.TILE_SIZE, Main.TILE_SIZE, null);
		g.dispose();
		return new Sprite(reverseImage);
	}
	
	public boolean isAutomatic(){
		return automatic;
	}
	
	public void setAutomatic(boolean auto){
		automatic = auto;
	}
	
}
