package com.hanfeldt.game.display;

import java.awt.Graphics;

public class InventorySprite extends Sprite{
	
	private String name;
	
	public static final int SMALL = 10;
	public static final int MEDIUM = 20;
	public static final int LARGE = 30;
	public static final int HUGE	 = 40;

	public InventorySprite(SpriteSheet sheet, int x, int y, int w, int h, String name) {
		super(sheet, x, y, w, h);
		this.name = name;
	}
	
	public InventorySprite(Sprite s, String name){
		super(s.getImage());
		this.name = name;
	}
	
	public void draw(Graphics g, int x, int y, int size){
		g.drawImage(getImage(), x - getWidth()/2, y - getWidth()/2, getWidth() * size / 10, getHeight() * size / 10, null);
//		g.drawLine(x, y, x+2, y+2);
//		g.drawImage(getImage(), x, y, getWidth() , getHeight() , null);
	}
	
	public String getName(){
		return name;
	}

}
