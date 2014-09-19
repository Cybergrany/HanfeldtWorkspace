package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;

public class Background {
	public int layerAmount;//The amount of layers in use in this level. 
	
	private BackgroundSheet[] layer;
	private Sprite[] staticLayer;
//	private Sky sky;
	private int currentLevel;
	private boolean sbg = false;//Static bg
	
	public Background(int level){
//		sky = new Sky();
		currentLevel = level + 1;
		layerAmount = LevelLoader.currentLevelBgAmount;
		
		layer = new BackgroundSheet[layerAmount];
		
		for(int i = 0; i < layerAmount; i++){
			layer[i] = new BackgroundSheet(levelPath(currentLevel, i, false));
		}
		sbg = LevelLoader.hasStaticBg;
		if(sbg){
			staticLayer = new Sprite[1];
			staticLayer[0] = new Sprite(new SpriteSheet(levelPath(currentLevel, 0, true)));
		}
	}
	
	public void tick(){
//		sky.tick();
	}
	
	public void draw(Graphics g){
//		sky.draw(g);
		for(int i = 0; i < layer.length; i++){
			layer[i].draw(g, viewModifierX(i), 0);
		}
		if(sbg)
		for(int i = 0; i < staticLayer.length; i++){
			staticLayer[i].draw(g, -Main.getGame().getPlayer().getX(), - Main.getGame().getPlayer().getY());
		}
	}
	
	private double viewModifierX(int i){
		if(i == 0){//Far items
			return - Main.getGame().getPlayer().getX() / layerAmount;
		}else if(i == layer.length - 1){//Close items
			return - Main.getGame().getPlayer().getX();
		}else {
			return Main.getGame().getPlayer().getX() / (i -layerAmount);
		}
	}
	
	@SuppressWarnings("unused")
	private double viewModifierY(int i){
		if(i == 0){
			return - Main.getGame().getPlayer().getY() / layerAmount;
		}else if(i == layer.length - 1){
			return - Main.getGame().getPlayer().getY();
		}else{
			return Main.getGame().getPlayer().getY() / (i - layerAmount);
		}
	}
	
	private String levelPath(int levelNumber, int layer, boolean staticBg){
		if(staticBg){
			return String.format("/images/maps/backgrounds/level%d/static/bg%d.png", levelNumber, layer);
		}
		return String.format("/images/maps/backgrounds/level%d/bg%d.png", levelNumber, layer);
	}
	
//	private String levelPath(int levelNumber){
//		return String.format("/images/maps/backgrounds/level%d/", levelNumber);
//	}

}
