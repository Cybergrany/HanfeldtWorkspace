package com.hanfeldt.game.state.menus;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreen;
import com.hanfeldt.game.state.State;

public class OptionMenu1State extends State{
	private OptionScreen optionScreen;

	public OptionMenu1State(Main main){
		super(main);
		optionScreen = new OptionScreen("/images/optionSplash.png");
	}
	
	public void tick(){
		optionScreen.tick();
	}

	public void draw(Graphics g){
		optionScreen.draw(g);
	}
}
