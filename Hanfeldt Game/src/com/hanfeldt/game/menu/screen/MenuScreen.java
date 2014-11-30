package com.hanfeldt.game.menu.screen;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.io.Sound;

public class MenuScreen {

	public int optionAmount = 0;
	private Background background;
	private int optionChosen = -1;
	private int optionSelected = 0;
	private int arrayPos = 0;//Temporary feex for thingy
	private boolean mouseInUse = true;
	private boolean selectionChanged = false;
	private MenuScreenOption[] options;
	
	public MenuScreen(String path, MenuScreenOption[] options){
		this.options = options;
		background = new Background(path);
		options[0].setSelected(true);//First option selected by default
	}
	
	public void tick(){
		Rectangle mouseRect = new Rectangle(Main.mouseX, Main.mouseY, 1, 1);
		for(int i=0; i<options.length; i++) {
			if(options[i].getBounds().intersects(mouseRect)){
				if(getSelectedOption() != options[i].getId()){
					selectionChanged = true;
				}
				options[i].setSelected(true);
				mouseInUse = true;
			}else if (mouseInUse){
				options[i].setSelected(false);
			}
			if(Main.getGame().getListener().mouseDown && !Main.getGame().getListener().mouseDownLastTick && options[i].getBounds().intersects(mouseRect)){
				setOptionChosen(getSelectedOption());
			}
		}
		if(selectionChanged){
			Sound.playSound("option_select.ogg");
			selectionChanged = false;
		}
		
		if(Main.getGame().getListener().downArrowDown){
			if(getOptionSelected() + 1 > getOptionAmount() - 1){
				setOptionSelected(0);
			}else{
				setOptionSelected(getOptionSelected() + 1);
			}
			setMouseInUse(false);
			Main.getGame().getListener().downArrowDown = false;//Temporary feex to stop the key from being terribly spammed
		}
		
		if(Main.getGame().getListener().upArrowDown){
			if(getOptionSelected() - 1 < 0){
				setOptionSelected(getOptionAmount() - 1);
			}else{
				setOptionSelected(getOptionSelected() - 1);
			}
			setMouseInUse(false);
			Main.getGame().getListener().upArrowDown = false;
		}
		
		if(Main.getGame().getListener().enterDown){
			setOptionChosen(getSelectedOption());
			Main.getGame().getListener().enterDown = false;//Probably not necessary but it'll be grand shure
		}
		
	}
	
	public void draw(Graphics g){
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
	public int getSelectedOption() {
		for(int i=0; i<options.length; i++) {
			if(options[i].selected) {
				arrayPos = i;
				return options[i].getId();
			}
		}
		return 0;
	}
	public int getOptionSelected(){
		return optionSelected;
	}
	
	public void setOptionSelected(int option){
		options[optionSelected].setSelected(false);
		optionSelected = option;
		options[optionSelected].setSelected(true);
		Sound.playSound("option_select.ogg");
	}
	
	public void setOptionChosen(int option){
		optionChosen = options[arrayPos].getId();
		optionAction(optionChosen);
		Sound.playSound("option_chosen.ogg");
	}
	
	public void setMouseInUse(boolean inUse){
		mouseInUse = inUse;
	}
	
	public int getOptionChosen(){
		return optionChosen;
	}
	
	public int getOptionAmount(){
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
