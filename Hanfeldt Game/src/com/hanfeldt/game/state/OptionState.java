package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.OptionScreen;

public class OptionState extends State{
	private OptionScreen optionScreen;

	public OptionState(Main main){
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
