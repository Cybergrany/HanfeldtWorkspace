package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.util.ArrayList;

import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.projectile.Bullet;

public class Layer {
	
	private backgroundStatic bg;
	private ArrayList<Entity> entities;
	private ArrayList<Bullet> bullets;
	
	public Layer(backgroundStatic b){
		bg = b;
		entities = new ArrayList<Entity>();
		bullets = new ArrayList<Bullet>();
	}
	
	public void draw(Graphics g, Camera c){
		for(Entity e : entities){
			if(e instanceof EntityLiving){
			c.renderEntityLiving(g,(EntityLiving) e);
			((EntityLiving) e).getWeaponEquipped().draw(g, (EntityLiving) e, c);
			}
		}
		for(Bullet b : bullets){
			if(!b.hasSprite){
				c.renderBullet(g, b);
			}else{
				if(b.direction){
					c.renderBullet(g, b, b.getSprite());
				}else{
					c.renderBullet(g, b, b.getReverseSprite());
				}
			}
		}
		bg.draw(g);
	}
	
	public backgroundStatic getBackground(){
		return bg;
	}
	
	public void addBullet(Bullet b){
		bullets.add(b);
	}
	
	public void removeBullet(Bullet b){
		bullets.remove(b);
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
}
