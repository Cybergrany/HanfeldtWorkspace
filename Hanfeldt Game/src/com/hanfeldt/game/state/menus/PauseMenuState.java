package com.hanfeldt.game.state.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.PauseScreen;
import com.hanfeldt.game.state.State;

public class PauseMenuState extends State{
	private PauseScreen pauseScreen;
	
	public PauseMenuState(Main main){
		super(main);
		pauseScreen = new PauseScreen("");
	}
	
	public void tick(){
		pauseScreen.tick();
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(0, 0, 0, 90));
		g.fillRect(0, 0, Main.sizeX, Main.sizeY);
		pauseScreen.draw(g);
	}
}
