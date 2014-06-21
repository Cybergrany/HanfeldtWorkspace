package com.hanfeldt.game.menu;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.state.OptionState;
import com.hanfeldt.game.state.Playing;

/**
 * Any code to be performed when an option is selected can be found here.
 * The ID's of all of these actions are also here.
 * @author David Ofeldt
 *
 */
public final class MenuScreenOptionAction {
	
	public static final int startGame = 0;
	public static final int openOptions = 1;
	public static final int loadGame = 2;
	public static final int quitGame = 3;
	
	public static final int back = 4;
	
	public static final int options_graphics = 5;
	public static final int options_sound = 6;
	
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
	
	public static void startGame(){
		Main.gameStarted = true;
		Main.splashShowing = false;
		Main.getGame().setState(new Playing(Main.getGame()));
	}
	
	public static void openOptions(){
		Main.getGame().setState(new OptionState(Main.getGame()));
	}

	public static void quitGame(){
		System.exit(0);
	}
	
	public static void goBack(){
		
	}
}
