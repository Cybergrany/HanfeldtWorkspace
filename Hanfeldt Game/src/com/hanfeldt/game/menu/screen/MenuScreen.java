package com.hanfeldt.game.menu.screen;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.Background;

public class MenuScreen {

	public int optionAmount = 0;
	private Background background;
	private static int optionChosen = -1;
	private static int optionSelected = 0;
	private static boolean mouseInUse = true;
	private static MenuScreenOption[] options;
	
	public MenuScreen(String path, MenuScreenOption[] options){
		MenuScreen.options = options;
		background = new Background(path);
		options[0].setSelected(true);
	}
	
	public void tick(){
		Rectangle mouseRect = new Rectangle(Main.mouseX, Main.mouseY, 1, 1);
		for(int i=0; i<options.length; i++) {
			if(options[i].getBounds().intersects(mouseRect)){
				options[i].selected = true;
				mouseInUse = true;
			}else if (mouseInUse){
				options[i].selected = false;
			}
		}
	}
	
	public void draw(Graphics g){
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
	public static int getSelectedOption() {
		for(int i=0; i<options.length; i++) {
			if(options[i].selected) {
				return options[i].getId();
			}
		}
		return 0;
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
	
	public static void setMouseInUse(boolean inUse){
		mouseInUse = inUse;
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
