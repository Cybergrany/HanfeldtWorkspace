package com.hanfeldt.game.level;

import java.util.ArrayList;

import com.hanfeldt.game.entity.Entity;

public class Layer {
	
	private backgroundStatic bg;
	private ArrayList<Entity> entities;
	
	
	public Layer(backgroundStatic b){
		bg = b;
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
