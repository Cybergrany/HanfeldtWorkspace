package com.hanfeldt.game.menu.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuScreenOption {
	
	private String option;
	private int x, y, id;
	boolean selected;
	
	public MenuScreenOption(String option, int x, int y, int id){
		this.option = option;
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public String getOption() {
		return option;
	}
	
	public int getId(){
		return id;
	}
	
	public void setSelected(boolean sel){
		selected = sel;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		if(selected) {
			g.drawString("<" + option + ">", x -7, y);
		}else{
			g.drawString(option, x, y);
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y -7, option.length() *7 -7, 10);
	}
	
}
