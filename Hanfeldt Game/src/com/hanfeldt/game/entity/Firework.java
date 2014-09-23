package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.io.Sound;

public class Firework extends Entity {
	private boolean shooting = true;
	private Random rand;
	private int shootingTicksTotal;
	private int lifetimeAfterExplode = 60;
	private float alpha = 1f;
	private Color color;
	private float speed;
	private ArrayList<Point> points;
	private boolean exploded = false;
	
	public Firework(int x, int y, Color color) {
		super(x, y);
		speed = 1;
		this.color = color;
		rand = new Random();
		shootingTicksTotal = rand.nextInt(60) + 60;
		points = new ArrayList<Point>();
		velX = rand.nextFloat() *1f * (rand.nextFloat() *1f *(rand.nextBoolean() ? -1 : 1));
		velY = -speed;
	}
	
	public void tick() {
		if(shooting) {
			points.add(new Point(getX(), getY()));
			if(totalTicks >= shootingTicksTotal) {
				shooting = false;
			}
			changeX(velX);
			changeY(velY);
		}else{
			if(!exploded) {
				//Explosion code. I know my commenting isn't very consistent, but some is better than none! :P
				Sound.playSound("FireworkBoom.wav");
				int particlesSize = rand.nextInt(8) +16;
				int lastShootIndex = points.size() -1;
				for(int i=0; i<particlesSize; i++) {
					points.add(new Point(points.get(lastShootIndex).x + rand.nextInt(64) -32, points.get(lastShootIndex).y + rand.nextInt(40) -20));
				}
				exploded = true;
			}
			alpha -= 0.02f;
			if(alpha < 0f) alpha = 0;
			if(totalTicks >= lifetimeAfterExplode + shootingTicksTotal) {
				//May as well do this, as it won't be used outside of gamewon and arcade
				if(Main.getGame().getState() instanceof GameWon){
					removeFirework(GameWon.fireworks);
				}else{
//					removeFirework(LevelArcade.fireworks);
				}
			}
		}
		totalTicks++;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color((float) color.getRed() /255, (float) color.getGreen() /255, (float) color.getBlue() /255, alpha));
		for(int i=0; i<points.size(); i++) {
			g.fillRect(points.get(i).x, points.get(i).y, 1, 1);
		}
	}
	
	public void removeFirework(ArrayList<Firework> f) {
		for(int i=0; i < f.size(); i++) {
			if(f.get(i).equals(this)) {
				f.remove(i);
			}
		}
	}
}
