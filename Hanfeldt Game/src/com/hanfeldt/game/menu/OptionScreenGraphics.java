package com.hanfeldt.game.menu;

import com.hanfeldt.game.menu.screen.MenuScreen;
import com.hanfeldt.game.menu.screen.MenuScreenOption;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;

public class OptionScreenGraphics extends MenuScreen{
	
	private static MenuScreenOption[] options = {new MenuScreenOption("Back", 10, 10, MenuScreenOptionAction.back),
																						new MenuScreenOption("Window Size +", 10, 25, MenuScreenOptionAction.changeScreenSizeUp),
																						new MenuScreenOption("Window Size -", 10, 36, MenuScreenOptionAction.changeScreenSizeDown)};
	
	public OptionScreenGraphics(String backgroundPath){
		super(backgroundPath, options);
	}
}
