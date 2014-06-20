package com.hanfeldt.game.splash;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.io.Listener;

/**
 * We need a splash screen.. I guess...
 * @author David Ofeldt
 *
 */
public class SplashScreen {
	private Background background; //This only seems neccesary if we animate the background, I guess we'll get to that later
	private static final int optionsX = 10, optionsHeight = 10;
	private static final int optionsStartY = 90;
	private Listener listener;
	private int optionSelected = 0;
	private SplashMenuOption[] options = new SplashMenuOption[] {new SplashMenuOption("Start Game", optionsX, optionsStartY, true),
																 new SplashMenuOption("Options", optionsX, optionsStartY + optionsHeight, false), 
																 new SplashMenuOption("Quit", optionsX, optionsStartY + (optionsHeight *2), false)};
	
	public SplashScreen(String backgroundPath, Listener l){
		background = new Background(backgroundPath);
		listener = l;
	}
	
	public void tick() {
		Rectangle mouseRect = new Rectangle(Main.mouseX, Main.mouseY, 1, 1);
		for(int i=0; i<options.length; i++) {
			options[i].selected = options[i].getBounds().intersects(mouseRect);
		}
		// I should really comment more...
		// The following code ensures less than 2 options are selected. (although I think this never happens, it's good to be sure lel)
		int count = 0;
		for(int i=0; i<options.length; i++) {
			if(options[i].selected) {
				count++;
			}
		}
		if(count > 1) {
			for(int i=0; i<options.length; i++) {
				options[i].selected = false;
			}
			options[0].selected = true;
		}
		
		
	}
	
	public void draw(Graphics g) {
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
	public SplashMenuOption getSelectedOption() {
		for(int i=0; i<options.length; i++) {
			if(options[i].selected) {
				return options[i];
			}
		}
		return null;
	}
	
}
