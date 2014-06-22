package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;

public class GameWon extends State {
	
	public GameWon(Main main) {
		super(main);
	}
	
	public void tick() {
		
	}
		
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.sizeX, Main.sizeY);
		g.setColor(Color.WHITE);
		g.drawString("Congratulations!", Main.sizeX /2 - (("Congratulation!".length() /2) *7), Main.sizeY /4);
		g.drawString("You win!", Main.sizeX /2 - (("You win!".length() /2) *7), Main.sizeY /2);
	}

}
