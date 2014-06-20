package com.hanfeldt.game.splash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SplashMenuOption {
	
	private String option;
	private int x, y;
	boolean selected;
	
	public SplashMenuOption(String option, int x, int y, boolean selected){
		this.selected = selected;
		this.option = option;
		this.x = x;
		this.y = y;
	}
	
	public String getOption() {
		return option;
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
