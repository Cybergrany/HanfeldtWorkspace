package com.hanfeldt.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 4796241145459172752L;
	private BufferedImage screen;
	
	public GamePanel(BufferedImage screen, int sizeX, int sizeY) {
		this.screen = screen;
		Dimension panelDimension = new Dimension(sizeX, sizeY); 
		setMinimumSize(panelDimension);
		setMaximumSize(panelDimension);
		setPreferredSize(panelDimension);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(screen, 0, 0, getWidth(), getHeight(), null);
	}
	
}
