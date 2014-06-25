package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.shop.Label;

public class Shop extends State {
	private static BufferedImage background;
	private Label[] labels;
	
	public Shop(Main main) {
		super(main);
		try {
			background = ImageIO.read(Shop.class.getResource("/images/ShopBackground.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		Label.setListener(main.getListener());
		labels = new Label[] {new Label("pistol", 50, 20, 30, Color.ORANGE, 0, 4, 1, 1),
							  new Label("sword", 0, 100, 30, Color.ORANGE, 4, 3, 1, 1)};
	}
	
	public void tick() {
		for(int i=0; i<labels.length; i++) {
			labels[i].tick();
		}
		if(main.getListener().shopButtonDown && !main.getListener().shopButtonDownLastTick) {
			main.returnToPlaying();
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, Main.sizeX, Main.sizeY, null);
		g.drawString("SHOP", Main.sizeX /2 -("SHOP".length() /2 *10), 15);
		for(int i=0; i<labels.length; i++) {
			labels[i].draw(g);
		}
	}
	
}
