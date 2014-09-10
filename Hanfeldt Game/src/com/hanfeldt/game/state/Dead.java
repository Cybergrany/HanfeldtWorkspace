package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Hud;

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
			main.respawnPlayer();
			main.returnToPlaying();
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		if(!Main.gameOver){
			main.getHud().getCharacterHead().draw(g, Main.WIDTH /2 - (Main.TILE_SIZE *2), (Main.HEIGHT /2) - 60);
			Font font = new Font("Arial", Font.PLAIN, 10);
			g.setFont(font);
			g.setColor(new Color(255, 255, 255));
			g.drawString("x " + Integer.toString(main.getLives()), (Main.WIDTH /2) - 5, (Main.HEIGHT /2) - 49);
		}else{
			g.setColor(Hud.transDark);
			g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			g.setColor(Hud.defWhite);
			g.drawString("Game Over", Main.WIDTH / 2 - 20, Main.HEIGHT / 2);
		}
	}
	
}
