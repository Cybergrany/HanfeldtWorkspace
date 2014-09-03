package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class Background {
	public int layerAmount;//The amount of layers in use in this level. 
	
	private BackgroundSheet[] layer;
	private Sky sky;
	private int currentLevel;
	
	public Background(int level){
		sky = new Sky();
		currentLevel = level + 1;
//		layerAmount = Main.getGame().resourceManager.getImageResourcesInDir(levelPath(currentLevel));
		layerAmount = LevelLoader.currentLevelBgAmount;
		
		layer = new BackgroundSheet[layerAmount];
		
		for(int i = 0; i < layerAmount; i++){
			layer[i] = new BackgroundSheet(levelPath(currentLevel, i));
		}
	}
	
	public void tick(){
		sky.tick();
	}
	
	public void draw(Graphics g){
		sky.draw(g);
		for(int i = 0; i < layer.length; i++){
			layer[i].draw(g, viewModifier(i), 0);
		}
	}
	
	private double viewModifier(int i){
		if(i == 0){//Far items
			return - Main.getGame().getPlayer().getX() / layerAmount;
		}else if(i == layer.length - 1){//Close items
			return - Main.getGame().getPlayer().getX();
		}else {
			return Main.getGame().getPlayer().getX() / (i -layerAmount);
		}
	}
	
	private String levelPath(int levelNumber, int layer){
		return String.format("/images/maps/backgrounds/level%d/bg%d.png", levelNumber, layer);
	}
	
//	private String levelPath(int levelNumber){
//		return String.format("/images/maps/backgrounds/level%d/", levelNumber);
//	}

}
