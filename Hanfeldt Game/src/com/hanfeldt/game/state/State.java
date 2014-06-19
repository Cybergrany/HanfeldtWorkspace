package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;

public abstract class State {
	protected Main main;
	
	public State(Main main) {
		this.main = main;
	}
	
	public abstract void tick();
	public abstract void draw(Graphics g);
	
}
