package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.splash.SplashScreen;

public class SplashState extends State {
	private SplashScreen splash;
	
	public SplashState(Main main) {
		super(main);
		splash = new SplashScreen("/images/Splash.png", main.getListener());
	}
	
	public void tick() {
		splash.tick();
	}
	
	public void  draw(Graphics g) {
		splash.draw(g);
	}
	
}
