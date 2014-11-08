package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class backgroundStatic extends Background{
	private BackgroundSheet sheet;
	
	private int currentLevel;
	
	public backgroundStatic(int level, int layer) {
		currentLevel = level;
		Main.getGame().getLevels().addBgToLayer(this);
		sheet = new BackgroundSheet(levelPath(currentLevel, layer, true), 2);
	}
	
	public void draw(Graphics g){
		sheet.drawEnlarged(g, -Main.getGame().getCamera().getX(), -Main.getGame().getCamera().getY());
	}
}
