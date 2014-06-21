package com.hanfeldt.game.menu;


public class OptionScreen extends MenuScreen{
	
	private static final int optionsX = 10;
	
	private static MenuScreenOption[] options = new MenuScreenOption[] {new MenuScreenOption("Back", optionsX, 20, MenuScreenOptionAction.back), 
																		new MenuScreenOption("Sound", optionsX, 35, MenuScreenOptionAction.options_sound),
																		new MenuScreenOption("Graphics", optionsX, 50, MenuScreenOptionAction.options_graphics)}; 
	
	public OptionScreen(String backgroundPath){
		super(backgroundPath, options);
	}

}
