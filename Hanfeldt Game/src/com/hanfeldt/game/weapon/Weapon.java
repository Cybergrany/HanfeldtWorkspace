	package com.hanfeldt.game.weapon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.EntityLiving;

public abstract class Weapon {
	protected Sprite sprite;
	protected EntityLiving entity;
	protected EntityItem linkedItem;
	protected long totalTicks = 0;
	public boolean automatic = true, hasOverlay = false;
	
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
	
	public void draw(Graphics g, EntityLiving e, Camera c){
		if(e.getWeaponEquipped() != null){
			Weapon equipped = e.getWeaponEquipped();
			if(equipped instanceof WeaponSwung){
				WeaponSwung tw = (WeaponSwung) equipped;
				if(tw.isTriggered()){
					if(e.getDirection()){
						c.renderSprite(g, tw.getSprite(), e.getX() +10, e.getY() +Main.TILE_SIZE /2);
					}else{
						c.renderSprite(g, e.getWeaponEquipped().getReverseSprite(), e.getX() - 10, e.getY() +Main.TILE_SIZE /2);
					}
				}else{
					if(e.getDirection()) {
						c.renderSprite(g, tw.getNotTriggeredSprite(), e.getX() +5, e.getY() +2);
					}else{
						c.renderSprite(g, tw.getNotTriggeredSprite(), e.getX() -5, e.getY() +2);
					}
				}
			}else{
				if(e.getDirection()){
					c.renderSprite(g, e.getWeaponEquipped().getSprite(), e.getX() +10, e.getY() +Main.TILE_SIZE /2);
				}else{
					c.renderSprite(g, e.getWeaponEquipped().getReverseSprite(), e.getX() - 10, e.getY() +Main.TILE_SIZE /2);
				}
			}
			if(e.getWeaponEquipped().hasOverlay){
				e.getWeaponEquipped().drawOverlay(g);
			}
		}
	}
	
	public void drawOverlay(Graphics g){}
	
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
	
	public abstract String name();
	
	public EntityItem getLinkedItem(){
		return new EntityItem(sprite, entity.getX(), entity.getY(), this);
	}
	
}
