package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class Background {
	public int layerAmount;//The amount of layers in use in this level. 
	
	private BackgroundSheet[] layer, staticLayer, foreStaticLayer;
//	private Sky sky;
	private int currentLevel, sectorY;
	private boolean sbg = false, fore = false;//Static, non moving bg, and static foreground
	
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
			staticLayer = new BackgroundSheet[1];
			staticLayer[0] = new BackgroundSheet(levelPath(currentLevel, 0, true), 2);;
			staticLayer[0].isStatic = true;
		}
		sectorY = layer[0].getImage().getHeight();
		System.out.println(sectorY);
	}
	
	public void tick(){
//		sky.tick();
	}
	
	public void draw(Graphics g){
//		sky.draw(g);
		for(int i = 0; i < layer.length; i++){
			layer[i].draw(g, viewModifierX(i), viewModifierY(i - layerAmount));
		}
		if(sbg)
		for(int i = 0; i < staticLayer.length; i++){
			staticLayer[i].drawEnlarged(g, -Main.getGame().getCamera().getX(), -Main.getGame().getCamera().getY());
		}
	}
	
	public void draw(Graphics g, boolean foreGround){
		
	}
	
	public double viewModifierX(int i){
		if(i == 0){//Far items
			return - Main.getGame().getCamera().getX() / layerAmount;
		}else if(i == layer.length - 1){//Close items
			return - Main.getGame().getCamera().getX();
		}else {
			return Main.getGame().getCamera().getX() / (i -layerAmount);
		}
	}
	
	public double viewModifierY(int i){
//		Main main = Main.getGame();
		return 0;
	}
	
	private String levelPath(int levelNumber, int layer, boolean staticBg){
		if(staticBg){
			return String.format("/images/maps/backgrounds/level%d/static/bg%d.png", levelNumber, layer);
		}
		return String.format("/images/maps/backgrounds/level%d/bg%d.png", levelNumber, layer);
	}

}
