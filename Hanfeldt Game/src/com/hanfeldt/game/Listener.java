package com.hanfeldt.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements MouseListener, KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		
		case(KeyEvent.VK_ESCAPE): // I think most games use escape? Maybe
			Main.escDown = true;
			break;
			
		case(KeyEvent.VK_D):
			Main.dDown = true;
			break;
		
		case(KeyEvent.VK_A):
			Main.aDown = true;
			break;
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		
		case(KeyEvent.VK_ESCAPE): // I think most games use escape? Maybe
			Main.escDown = false;
		case(KeyEvent.VK_D):
			Main.dDown = false;
			break;
		case(KeyEvent.VK_A):
			Main.aDown = false;
			break;
		case(KeyEvent.VK_SPACE): // Space or W for jump?
			Main.spaceDown = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

}
