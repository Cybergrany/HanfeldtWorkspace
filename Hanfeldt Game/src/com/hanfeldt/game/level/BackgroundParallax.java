package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class BackgroundParallax extends Background{
	private BackgroundSheet[] sheet;
	
	private int currentLevel, layerAmount;
	
	public BackgroundParallax(int level){
		currentLevel = level;
		layerAmount = LevelLoader.currentLevelBgAmount;
		
		sheet = new BackgroundSheet[layerAmount];
		
		for(int i = 0; i < layerAmount; i++){
			sheet[i] = new BackgroundSheet(levelPath(currentLevel, i, false));
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < sheet.length; i++){
			sheet[i].draw(g, viewModifierX(i), viewModifierY(i - layerAmount));
		}
	}
	
	public double viewModifierX(int i){
		if(i == 0){//Far items
			return - Main.getGame().getCamera().getX() / layerAmount;
		}else if(i == sheet.length - 1){//Close items
			return - Main.getGame().getCamera().getX();
		}else {
			return Main.getGame().getCamera().getX() / (i -layerAmount);
		}
	}
	
	public double viewModifierY(int i){
//		Main main = Main.getGame();
		return 0;
	}
}
