package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Firework;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.server.Client;

public class GameWon extends State {
	public static ArrayList<Firework> fireworks;
	private Random rand;
	private long nextFireworkTick = 0;
	
	public GameWon(Main main) {
		super(main);
		fireworks = new ArrayList<Firework>();
		rand = new Random();
		nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(30) +30;
		
		if(Main.username == null || Main.username == "user"){
			MenuScreenOptionAction.setUsername();
		}
		Client.sendScores();
	}
	
	public void tick() {
		if(Main.getGame().getTotalTicks() >= nextFireworkTick) {
			switch(rand.nextInt(3)) {
			case 0:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.RED));
				break;
			case 1:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.BLUE));
				break;
			case 2:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.ORANGE));
				break;
			}
			nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(16) +16;
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).tick();
		}
	}
		
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString("Congratulations!", Main.WIDTH /2 - (("Congratulation!".length() /2) *7), Main.HEIGHT /4);
		g.drawString("You win!", Main.WIDTH /2 - (("You win!".length() /2) *7), Main.HEIGHT /2);
		g.drawString("You finished with a score of:", Main.WIDTH /2 - (("You finished with a score of:".length() /2) *5), Main.HEIGHT /2 +30);
		String scoreString = Long.toString(main.getPlayer().getScore());
		g.drawString(scoreString, Main.WIDTH /2 - (scoreString.length() /2) *5, Main.HEIGHT /2 +40);
		if(Client.hsServerAvailable) {
			String string = "Hiscore server unavailable/waiting to transfer scores";
			g.drawString(string, Main.WIDTH /2 - (string.length() /2) *5, Main.HEIGHT /2 +50);
		}else{
			String string = "Best score: " + Client.hsName + " with a score of " + Client.bestHs;
			g.drawString(string, Main.WIDTH /2 - (string.length() /2) *5, Main.HEIGHT /2 +50);
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).render(g);
		}
	}
	
}
