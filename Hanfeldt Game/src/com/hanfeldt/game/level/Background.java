package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class Background {
	public int layerAmount = 4;//The amount of layers in use in this level. 
	
	private BackgroundSheet[] layer;
	private Sky sky;
	
	public Background(){
		sky = new Sky();
		
		layer = new BackgroundSheet[layerAmount];
		
		for(int i = 0; i < layerAmount; i++){
			layer[i] = new BackgroundSheet(String.format("/images/maps/backgrounds/level%d/bg%d.png", 1, i));
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

}
