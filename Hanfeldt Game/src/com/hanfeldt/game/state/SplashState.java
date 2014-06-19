package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.splash.SplashScreen;

public class SplashState {
	private SplashScreen splash;
	
	public SplashState(Main main) {
		splash = new SplashScreen("res/images/Splash.png", main.getListener());
	}
	
	public void tick() {
		
	}
	
	public void  draw(Graphics g) {
		
	}
	
}
