package com.hanfeldt.game.splash;

import java.awt.Graphics;

public class OptionScreen {
	
	private Background background;
	
	private static final int optionsX = 10;
	
	private static SplashMenuOption[] options = new SplashMenuOption[] {new SplashMenuOption("Back", optionsX, 20, true), 
																		new SplashMenuOption("Sound", optionsX, 35, false),
																		new SplashMenuOption("Graphics", optionsX, 50, false)}; 
	
	public OptionScreen(String backgroundPath){
		background = new Background(backgroundPath);
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}

}
