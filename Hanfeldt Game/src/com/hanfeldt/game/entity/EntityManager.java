package com.hanfeldt.game.entity;

import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.projectile.Bullet;

/**
 * This class ensures that all entites are moved around concurrently
 * The alternative to this would be iterating over every layer multiple times inside
 * tick methods, so I settled on using two seperate {@link ArrayList}s. Not sure if 
 * this is the most efficient way of doing this.... 
 * @author ofeldt
 *
 */
public class EntityManager {
	
	private ArrayList<Entity> entities;
	private ArrayList<Npc> npc;
	private ArrayList<Bullet> bullets;
	private ArrayList<EntityItem> items;
	
	private Main main;
	
	public EntityManager(Main m){
		entities = new ArrayList<Entity>();
		npc = new ArrayList<Npc>();
		bullets = new ArrayList<Bullet>();
		items = new ArrayList<EntityItem>();
		
		main = m;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
		main.getLayers().get(e.getLayer()).addEntity(e);
	}
	
	public void removeEntity(Entity e){
		entities.remove(e);
		main.getLayers().get(e.getLayer()).removeEntity(e);
	}
	
	public void addNpc(Npc n){
		npc.add(n);
		addEntity(n);
	}
	
	public void removeNpc(Npc n){
		npc.remove(n);
		removeEntity(n);
	}
	
	public void addBullet(Bullet b){
		bullets.add(b);
		main.getLayers().get(b.getLayer()).addBullet(b);
	}
	
	public void removeBullet(Bullet b){
		bullets.remove(b);
		main.getLayers().get(b.getLayer()).removeBullet(b);
	}
	
	public void addItems(EntityItem i){
		items.add(i);
		addEntity(i);
	}
	
	public void removeItems(EntityItem i){
		items.remove(i);
		removeEntity(i);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public ArrayList<Npc> getNpcs(){
		return npc;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public ArrayList<EntityItem>getItems(){
		return items;
	}
}
