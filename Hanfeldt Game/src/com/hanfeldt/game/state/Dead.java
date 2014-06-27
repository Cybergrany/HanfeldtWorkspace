package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hanfeldt.game.Hud;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.state.menus.MainMenuState;

public class Dead extends State {
	private static final int deadTicks = 240;
	private int deadFor = 0;
	private Main main;
	
	public Dead(Main main) {
		super(main);
		this.main = main;
	}
	
	public void tick() {
		deadFor++;
		if(deadFor >= deadTicks && !Main.gameOver) {
			main.setState(new Arcade(main));//TODO: Very temporary
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.sizeX, Main.sizeY);
		
		if(!Main.gameOver){
			main.getCharacter().draw(g, Main.sizeX /2 - (Main.tileSize *2), (Main.sizeY /2) - 60);
			Font font = new Font("Arial", Font.PLAIN, 10);
			g.setFont(font);
			g.setColor(new Color(255, 255, 255));
			g.drawString("x " + Integer.toString(main.getLives()), (Main.sizeX /2) - 5, (Main.sizeY /2) - 49);
		}else{
			g.setColor(Hud.transDark);
			g.fillRect(0, 0, Main.sizeX, Main.sizeY);
			g.setColor(Hud.defWhite);
			g.drawString("Game Over", Main.sizeX / 2 - 20, Main.sizeY / 2);
		}
	}
	
}
