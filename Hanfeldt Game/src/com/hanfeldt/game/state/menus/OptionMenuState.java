package com.hanfeldt.game.state.menus;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreen;
import com.hanfeldt.game.state.State;

public class OptionMenuState extends State{
	private OptionScreen optionScreen;

	public OptionMenuState(Main main){
		super(main);
		optionScreen = new OptionScreen("/images/MenuBackgrounds/optionMenu.png");
	}
	
	public void tick(){
		optionScreen.tick();
	}

	public void draw(Graphics g){
		optionScreen.draw(g);
	}
}
