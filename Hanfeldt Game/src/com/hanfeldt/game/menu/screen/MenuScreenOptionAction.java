package com.hanfeldt.game.menu.screen;

import javax.swing.JOptionPane;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.io.Sound;
import com.hanfeldt.game.menu.OptionScreen;
import com.hanfeldt.game.scripting.Dialogue;
import com.hanfeldt.game.server.Client;
import com.hanfeldt.game.state.Arcade;
import com.hanfeldt.game.state.State;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.state.menus.HighScoreDisplayandChangeState;
import com.hanfeldt.game.state.menus.MainMenuState;
import com.hanfeldt.game.state.menus.OptionMenuGraphicsState;
import com.hanfeldt.game.state.menus.OptionMenuState;

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
	public static final int startArcade = 0;
	public static final int startStory = 10;
	public static final int openOptions = 1;
	public static final int loadGame = 2;
	public static final int quitGame = 3;
	
	public static final int back = 4;
	
	public static final int options_graphics = 5;
	public static final int options_sound = 6;
	
	public static final int resumeGame = 7;
	public static final int muteGame = 8;
	
	public static final int gotoMainMenu = 9;
	public static final int setUsername = 11;
	public static final int openHighScores = 12;
	
	public static final int changeScreenSizeUp = 13;
	public static final int changeScreenSizeDown = 14;
	
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
		case startArcade:
			startArcade();
			break;
		case startStory:
			startStory();
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
		case options_graphics:
			openGraphicsOptions();
			break;
		case setUsername:
			setUsername();
			break;
		case openHighScores:
			openHighScores();
			break;
		case changeScreenSizeUp:
			changeScreenSizeUp();
			break;
		case changeScreenSizeDown:
			changeScreenSizeDown();
			break;
		}
	}
	
	/**
	 * Starts the game by disabling the menus ({@code Main.splashShowing = false} and then setting the 
	 * game state to {@link Arcade}
	 */
	public static void startArcade(){
//		Main.gameStarted = true;
//		Main.splashShowing = false;
//		Arcade arcade = new Arcade(Main.getGame());
//		Main.getGame().setState(arcade);
//		Main.getGame().setPlayingState(arcade);
	}
	
	public static void startStory(){
		Main.gameStarted = true;
		Main.splashShowing = false;
		Dialogue.loadImage("DialogueBox.png");
		State story = new Story(Main.getGame(), Main.getGame().getCamera());
		Main.getGame().setState(story);
		Main.getGame().setPlayingState(story);
	}
	
	/**
	 * Opens {@link OptionScreen}
	 */
	public static void openOptions(){
		setLastScreen(Main.getGame().getState());
		Main.getGame().setState(new OptionMenuState(Main.getGame()));
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
			Sound.pauseOggLoop();
		}else{
			Main.muted = false;
			Sound.resumeOggLoop();
		}
	}
	
	public static void gotoMainMenu(){
		//TODO:"Save game before quitting?" Dialogue. 
		Main.getGame().setState(new MainMenuState(Main.getGame()));
		if(Main.username == null || Main.username == "user"){
			setUsername();
		}
		Client.sendScores();
		if(Main.getGame().getState() instanceof Story || Main.getGame().getState() instanceof Arcade)
			Main.getGame().getLevels().clearLevel();
	}
	
	public static void openGraphicsOptions(){
		Main.getGame().setState(new OptionMenuGraphicsState(Main.getGame()));
	}
	
	public static void setUsername(){
		Main.username = JOptionPane.showInputDialog("Enter your username\n(Used for hiscores)");
		if(Main.username == null || Main.username.trim().isEmpty()) {
			Main.username = "user";
		}
	}
	
	public static void openHighScores(){
		Client.sendScores();
		setLastScreen(Main.getGame().getState());
		Main.getGame().setState(new HighScoreDisplayandChangeState(Main.getGame()));
	}
	
	public static void changeScreenSizeUp(){
//		Main.SCALE++;
	}
	
	public static void changeScreenSizeDown(){
//		Main.SCALE--;
	}
	
	private static void setLastScreen(State s){
		lastState = s;
	}
	
}
