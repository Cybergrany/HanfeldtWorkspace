package com.hanfeldt.game.menu.screen;

import java.awt.Graphics;

import com.hanfeldt.game.Main;


public class Tooltip {
	private MenuScreenOption[] options;
	
	/**
	 * A pop-up menu thingy
	 * @param x
	 * @param y
	 * @param option
	 */
	public Tooltip(MenuScreenOption[] option){
		options = option;
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		g.draw3DRect((Main.sizeX / 2) - (getLongestString() / 2), (Main.sizeY / 2) - ((options.length / 2) * 20), getLongestString() + 5, options.length * 20 , true);//Shmancy 3D
		for(int i = 0; i < options.length; i++){
			options[i].draw(g);
		}
	}
	
	private int getLongestString(){
		int length = 0;
		for(int i = 0; i < options.length; i++){
			if(options[i].toString().length() > length)
				length = options[i].toString().length();
		}
		return length;
	}
}
