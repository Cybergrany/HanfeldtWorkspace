package com.hanfeldt.game.display;

import java.awt.Graphics;

public class InventorySprite extends Sprite{
	
	private String name;

	public InventorySprite(SpriteSheet sheet, int x, int y, int w, int h, String name) {
		super(sheet, x, y, w, h);
		this.name = name;
	}
	
	public InventorySprite(Sprite s, String name){
		super(s.getImage());
		this.name = name;
	}
	
	public void draw(Graphics g, int x, int y, int size){
		
	}
	
	public String getName(){
		return name;
	}

}
