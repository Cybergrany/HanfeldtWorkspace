package com.hanfeldt.game.state.menus;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.MainMenuScreen;
import com.hanfeldt.game.state.State;

public class MainMenuState extends State{
	private MainMenuScreen splash;
	
	public MainMenuState(Main main) {
		super(main);
		splash = new MainMenuScreen("/images/Splash.png");
	}
	
	public void tick() {
		splash.tick();
	}
	
	public void  draw(Graphics g) {
		splash.draw(g);
	}
	
}
