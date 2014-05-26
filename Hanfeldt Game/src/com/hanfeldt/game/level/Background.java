package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;

public class Background {
	
	public int layerAmount = 1;
	private BackgroundSheet[] layer;
	private Sky sky;
	
	public Background(){
		sky = new Sky();
		
		layer = new BackgroundSheet[layerAmount];
		
		layer[0] = new BackgroundSheet("/images/bg1.png");
	}
	
	public void tick(){
		sky.tick();
	}
	
	public void draw(Graphics g){
		sky.draw(g);
		for(int i = 0; i < layer.length; i++){
			layer[i].draw(g, - Main.getGame().getPlayer().getX() / 2  + (Main.sizeX /2), 0);
		}
	}

}
