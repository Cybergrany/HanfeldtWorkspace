package com.hanfeldt.game.menu;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.screen.MenuScreen;
import com.hanfeldt.game.menu.screen.MenuScreenOption;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;

/**
 * We need a splash screen.. I guess...
 * Actually, nevermind. It turned into a main menu.
 * @author David Ofeldt
 *
 */
public class MainMenuScreen extends MenuScreen{
	
	private static final int optionsX = 10;
	private static MenuScreenOption[] options = new MenuScreenOption[] {new MenuScreenOption("Coming Soon!", optionsX, 60, MenuScreenOptionAction.startArcade),
																 new MenuScreenOption("Story Mode", optionsX, 75 , MenuScreenOptionAction.startStory), 
																 new MenuScreenOption("Options", optionsX, 90, MenuScreenOptionAction.openOptions), 
																 new MenuScreenOption("Load Game", optionsX, 105, MenuScreenOptionAction.loadGame),
																 new MenuScreenOption("High Scores", optionsX, 120, MenuScreenOptionAction.openHighScores),
																 new MenuScreenOption("Quit", optionsX, 135, MenuScreenOptionAction.quitGame)};
	
	public MainMenuScreen(String backgroundPath){
		super(backgroundPath, options);
		Main.splashShowing = true;
	}
}
