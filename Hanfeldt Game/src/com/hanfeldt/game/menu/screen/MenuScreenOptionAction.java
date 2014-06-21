package com.hanfeldt.game.menu.screen;

import org.omg.IOP.Codec;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreen;
import com.hanfeldt.game.state.OptionState;
import com.hanfeldt.game.state.Playing;

/**
 * Any code to be performed when an option is chosen can be found here.
 * The ID's of the options are also here.
 * @author David Ofeldt
 *
 */
public final class MenuScreenOptionAction {
	
	/*
	 * Below is a list of option ID's.
	 * Every option gets a unique id, and code can be run based on the id of that option(see below)
	 */
	public static final int startGame = 0;
	public static final int openOptions = 1;
	public static final int loadGame = 2;
	public static final int quitGame = 3;
	
	public static final int back = 4;
	
	public static final int options_graphics = 5;
	public static final int options_sound = 6;
	
	/**
	 * Performs an action based on the option passed into it.
	 * @param option - the ID of the option to be performed.
	 */
	public static void doAction(int option){
		switch(option){
		case openOptions:
			openOptions();
			break;
		case quitGame:
			quitGame();
			break;
		case startGame:
			startGame();
			break;
			
		}
	}
	
	/**
	 * Starts the game by disabling the menus ({@code Main.splashShowing = false} and then setting the 
	 * game state to {@link Playing}
	 */
	public static void startGame(){
		Main.gameStarted = true;
		Main.splashShowing = false;
		Main.getGame().setState(new Playing(Main.getGame()));
	}
	
	/**
	 * Opens {@link OptionScreen}
	 */
	public static void openOptions(){
		Main.getGame().setState(new OptionState(Main.getGame()));
	}
	
	/**
	 * Quits the game, using a standard quit code.
	 */
	public static void quitGame(){
		System.exit(0);
	}
	
	/**
	 * Go back to the previous menu.
	 * TODO:NOT YET IMPLEMENTED
	 */
	public static void goBack(){
		
	}
}
