package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;

public abstract class State {
	protected Main main;
	protected Camera camera;
	
	public State(Main main) {
		this.main = main;
		camera = new Camera(0, 0, main.getPlayer());
	}
	
	public abstract void tick();
	public abstract void draw(Graphics g);
	
}
