package com.hanfeldt.game.splash;

import java.awt.Color;
import java.awt.Graphics;

public class SplashMenuOption {
	
	private String option;
	private int x, y;
	boolean selected;
	
	public SplashMenuOption(String option, int x, int y, boolean selected){
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
		g.setColor(Color.WHITE);
		if(selected) {
			g.drawString("<" + option + ">", x, y);
		}else{
			g.drawString(option, x, y);
		}
	}
	
}
