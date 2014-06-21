package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Gore extends Entity {

	public Gore(int x, int y) {
		super(x, y);
		falling = true;
		velX = new Random().nextFloat() *2f;
		velY = new Random().nextFloat() * 4f;
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		System.out.println("Rendered Gore.");
		g.fillRect(getX(), getY(), 10, 10);
	}
	
}
