package com.hanfeldt.game.level;

import java.awt.Graphics;
import java.util.ArrayList;

import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;

public class Layer {
	
	private backgroundStatic bg;
	private ArrayList<Entity> entities;
	
	
	public Layer(backgroundStatic b){
		bg = b;
		entities = new ArrayList<>();
	}
	
	public void draw(Graphics g, Camera c){
		for(Entity e : entities){
			if(e instanceof EntityLiving)
			c.renderEntityLiving(g,(EntityLiving) e);
		}
		bg.draw(g);
	}
	
	public backgroundStatic getBackground(){
		return bg;
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
