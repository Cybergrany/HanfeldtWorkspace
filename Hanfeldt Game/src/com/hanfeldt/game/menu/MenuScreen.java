package com.hanfeldt.game.menu;

import java.awt.Graphics;

public class MenuScreen {

	public int optionAmount = 0;
	private Background background;
	private static int optionChosen = -1;
	private static int optionSelected = 0;
	private static MenuScreenOption[] options;
	
	public MenuScreen(String path, MenuScreenOption[] options){
		MenuScreen.options = options;
		background = new Background(path);
		options[0].setSelected(true);
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
	public MenuScreenOption getSelectedOption() {
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
		optionChosen = options[option].getId();
		optionAction(optionChosen);
	}
	
	public int getOptionChosen(){
		return optionChosen;
	}
	
	public static int getOptionAmount(){
		return options.length;
	}
	
	/**
	 * Executes an action from {@link MenuScreenOptionAction} based on what option is selected.
	 * @see MenuScreenOptionAction
	 * @param option - the ID of the option to be executed.
	 */
	private static void optionAction(int option){
		MenuScreenOptionAction.doAction(option);
	}
	
	
}
