package com.hanfeldt.game.state.menus;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreenGraphics;
import com.hanfeldt.game.state.State;

public class OptionMenuGraphicsState extends State{
	private OptionScreenGraphics optionScreen;
	
	public OptionMenuGraphicsState(Main main){
		super(main);
		optionScreen = new OptionScreenGraphics("/images/MenuBackgrounds/optionMenu.png");
	}
	
	public void tick(){
		optionScreen.tick();
	}

	public void draw(Graphics g){
		optionScreen.draw(g);
	}

}
