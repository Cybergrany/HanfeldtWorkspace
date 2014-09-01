package com.hanfeldt.game.level;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class Sky{
	
	private Sprite cloud, sun;
	
	public Sky(){
		cloud = new Sprite(Main.getSpritesheet(), 1, 0, 2, 1);
		sun = new Sprite(Main.getSpritesheet(), 0, 1, 2, 2);
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(0x00, 0xAA, 0xFF));
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		for (int i = 0; i < 5; i++) {
			cloud.draw(g, (i * 30) + 20, ((i % 2 == 0) ? 10 : 20));
		}
		sun.draw(g, 190, 10);
	}
}
