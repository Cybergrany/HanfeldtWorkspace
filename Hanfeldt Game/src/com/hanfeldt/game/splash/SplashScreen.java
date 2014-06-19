package com.hanfeldt.game.splash;

import java.awt.Graphics;

import com.hanfeldt.io.Listener;

/**
 * We need a splash screen.. I guess...
 * @author David Ofeldt
 *
 */
public class SplashScreen {
	private Background background;
	private static final int optionsX = 10;
	private Listener listener;
	private int optionSelected = 0;
	private SplashMenuOption[] options = new SplashMenuOption[] {new SplashMenuOption("Start Game", optionsX, 80, true),
																 new SplashMenuOption("Options", optionsX, 110, false)};
	
	public SplashScreen(String backgroundPath, Listener l){
		background = new Background(backgroundPath);
		listener = l;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
}
