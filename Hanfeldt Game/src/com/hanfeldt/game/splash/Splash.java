package com.hanfeldt.game.splash;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;

/**
 * We need a splash screen.. I guess...
 * @author David Ofeldt
 *
 */
public class Splash {
	private Background background;
	private static final int optionsX = 10;
	private SplashMenuOption[] options = new SplashMenuOption[] {new SplashMenuOption("Start Game", optionsX, 80, true),
																 new SplashMenuOption("Options", optionsX, 110, false)};
	
	public Splash(String backgroundPath){
		background = new Background(backgroundPath);
	}
	
	public void draw(Graphics g) {
		background.draw(g);
		for(int i=0; i<options.length; i++) {
			options[i].draw(g);
		}
	}
	
}
