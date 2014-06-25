package com.hanfeldt.game.menu.screen;

import java.security.acl.LastOwnerException;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreen;
import com.hanfeldt.game.state.Playing;
import com.hanfeldt.game.state.State;
import com.hanfeldt.game.state.menus.MainMenuState;
import com.hanfeldt.game.state.menus.OptionMenu1State;

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
	
	public static final int resumeGame = 7;
	public static final int muteGame = 8;
	
	public static final int gotoMainMenu = 9;
	
	private static State lastState;//The last option screen displayed.
	
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
		case back:
			goBack();
			break;
		case resumeGame:
			resumeGame();
			break;
		case muteGame:
			muteGame();
			break;
		case gotoMainMenu:
			gotoMainMenu();
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
		setLastScreen(Main.getGame().getState());
		Main.getGame().setState(new OptionMenu1State(Main.getGame()));
	}
	
	/**
	 * Quits the game, using a standard quit code.
	 */
	public static void quitGame(){
		System.exit(0);
	}
	
	/**
	 * Go back to the previous menu.
	 */
	public static void goBack(){
		Main.getGame().setState(lastState);
	}
	
	/**
	 * Resume the game
	 */
	public static void resumeGame(){
		Main.getGame().returnToPlaying();
	}
	
	/**
	 * Mutes the game
	 */
	public static void muteGame(){
		if(!Main.muted){
			Main.muted = true;
		}else{
			Main.muted = false;
		}
	}
	
	public static void gotoMainMenu(){
		//TODO:"Save game before quitting?" Dialogue. 
		Main.getGame().setState(new MainMenuState(Main.getGame()));
	}
	
	private static void setLastScreen(State s){
		lastState = s;
	}
	
}
