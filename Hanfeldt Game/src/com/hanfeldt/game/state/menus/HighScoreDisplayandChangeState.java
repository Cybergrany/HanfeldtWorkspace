package com.hanfeldt.game.state.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.HighScoreDisplayandChangeScreen;
import com.hanfeldt.game.server.Client;
import com.hanfeldt.game.state.State;

public class HighScoreDisplayandChangeState extends State{
	
	private HighScoreDisplayandChangeScreen screen;
	
	public HighScoreDisplayandChangeState(Main main){
		super(main);
		screen = new HighScoreDisplayandChangeScreen("/images/MenuBackgrounds/optionMenu.png");
	}
	
	public void tick() {
		screen.tick();
	}
	
	public void  draw(Graphics g) {
		screen.draw(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 9));
		g.drawString("Your username: " + Main.username, 5, Main.HEIGHT - 10);
		if(Client.hsServerAvailable) {
			String string = "Hiscore server unavailable/waiting to transfer scores";
			g.drawString(string, Main.WIDTH / 3 - (string.length() /2) *2, Main.HEIGHT /2 +10);
		}else{
			String string = "Best score: " + Client.hsName + " with a score of " + Client.bestHs;
			g.drawString(string, Main.WIDTH / 3 - (string.length() /2) *2, Main.HEIGHT /2 +10);
		}
	}	
}
