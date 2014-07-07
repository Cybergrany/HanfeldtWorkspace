package com.hanfeldt.game.menu;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.screen.MenuScreen;
import com.hanfeldt.game.menu.screen.MenuScreenOption;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;

public class HighScoreDisplayandChangeScreen extends MenuScreen{
	
	private static final int optionsX = 10;
	
	private static MenuScreenOption[] options = new MenuScreenOption[] {new MenuScreenOption("Back", optionsX, 15, MenuScreenOptionAction.back),
																		new MenuScreenOption("Set a username", optionsX, 35, MenuScreenOptionAction.setUsername)};
	
	public HighScoreDisplayandChangeScreen(String backgroundPath){
		super(backgroundPath, options);
		Main.splashShowing = true;
	}
}
