package com.hanfeldt.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GamePanel extends Canvas {
	private static final long serialVersionUID = 1L;
	private BufferedImage screen;
	
	public GamePanel(BufferedImage screen, int sizeX, int sizeY) {
		this.screen = screen;
		Dimension panelDimension = new Dimension(sizeX, sizeY); 
		setMinimumSize(panelDimension);
		setMaximumSize(panelDimension);
		setPreferredSize(panelDimension);
		setIgnoreRepaint(true);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(screen, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
}
