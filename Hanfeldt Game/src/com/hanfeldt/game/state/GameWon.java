package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Firework;

public class GameWon extends State {
	private ArrayList<Firework> fireworks;
	private Random rand;
	private long nextFireworkTick = 0;
	
	public GameWon(Main main) {
		super(main);
		fireworks = new ArrayList<Firework>();
		rand = new Random();
		nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(120) +60;
	}
	
	public void tick() {
		if(Main.getGame().getTotalTicks() >= nextFireworkTick) {
			switch(rand.nextInt(3)) {
			case 0:
				fireworks.add(new Firework(rand.nextInt(Main.sizeX), Main.sizeY, Color.RED));
				break;
			case 1:
				fireworks.add(new Firework(rand.nextInt(Main.sizeX), Main.sizeY, Color.BLUE));
				break;
			case 2:
				fireworks.add(new Firework(rand.nextInt(Main.sizeX), Main.sizeY, Color.ORANGE));
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
		g.fillRect(0, 0, Main.sizeX, Main.sizeY);
		g.setColor(Color.WHITE);
		g.drawString("Congratulations!", Main.sizeX /2 - (("Congratulation!".length() /2) *7), Main.sizeY /4);
		g.drawString("You win!", Main.sizeX /2 - (("You win!".length() /2) *7), Main.sizeY /2);
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).render(g);
		}
	}
	
	public void removeFirework(Firework f) {
		for(int i=0; i<fireworks.size(); i++) {
			if(fireworks.get(i).equals(f)) {
				fireworks.remove(i);
			}
		}
	}
	
}
