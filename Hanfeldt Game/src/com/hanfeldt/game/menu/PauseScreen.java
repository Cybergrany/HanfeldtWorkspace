package com.hanfeldt.game.menu;

import com.hanfeldt.game.menu.screen.MenuScreen;
import com.hanfeldt.game.menu.screen.MenuScreenOption;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;

public class PauseScreen extends MenuScreen{
	
	private static MenuScreenOption[] options = new MenuScreenOption[]{new MenuScreenOption("Resume Game", 10, 10, MenuScreenOptionAction.resumeGame),
																		new MenuScreenOption("Mute", 10, 24, MenuScreenOptionAction.muteGame),
																		new MenuScreenOption("Quit to Main Menu", 10, 38, MenuScreenOptionAction.gotoMainMenu),
																		new MenuScreenOption("Quit Game", 10, 52, MenuScreenOptionAction.quitGame)};
	
	public PauseScreen(String backgroundPath){
		super(backgroundPath, options);
	}

}
