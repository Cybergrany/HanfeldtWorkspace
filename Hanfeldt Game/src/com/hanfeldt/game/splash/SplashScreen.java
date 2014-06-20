package com.hanfeldt.game.splash;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.state.Playing;
import com.hanfeldt.io.Listener;

/**
 * We need a splash screen.. I guess...
 * @author David Ofeldt
 *
 */
public class SplashScreen {

	final int startGame = 0;
	final int gameSettings = 1;
	
	private Background background;
	private static final int optionsX = 10;
	private static int optionChosen = -1;
	private static int optionSelected = 0;
	private static SplashMenuOption[] options = new SplashMenuOption[] {new SplashMenuOption("Start Game", optionsX, 80, true),
																 new SplashMenuOption("Options", optionsX, 110, false)};
	
	public SplashScreen(String backgroundPath, Listener l){
		background = new Background(backgroundPath);
		options[optionSelected].setSelected(true);
		Main.splashShowing = true;
	}
	
	public void tick() {
		if(Main.gameStarted){
			Main.splashShowing = false;
			Main.getGame().setState(new Playing(Main.getGame()));
		}
		switch(getOptionChosen()){
			case -1:
				break;
			case startGame://Start Game
				Main.gameStarted = true;
			case gameSettings:
				break;
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
	public static int getOptionSelected(){
		return optionSelected;
	}
	
	public static void setOptionSelected(int option){
		options[optionSelected].setSelected(false);
		optionSelected = option;
		options[optionSelected].setSelected(true);
	}
	
	public static void setOptionChosen(int option){
		optionChosen = option;
	}
	
	public int getOptionChosen(){
		return optionChosen;
	}
	
	public static int getOptionAmount(){
		return options.length;
	}
	
}
