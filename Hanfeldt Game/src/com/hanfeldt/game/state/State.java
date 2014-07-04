package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;

public abstract class State {
	protected Main main;
	protected Camera camera;
	
	public State(Main main, Camera c) {
		this.main = main;
		camera = c;
	}
	
	public State(Main main) {
		this.main = main;
		camera = Main.getGame().getCamera();
	}
	
	public abstract void tick();
	public abstract void draw(Graphics g);
	
}
