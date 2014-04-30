package com.hanfeldt.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements MouseListener, KeyListener{
	
	static boolean pausePressed, debugPressed;

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
			
		case(KeyEvent.VK_D):
			Main.dDown = true;
			break;
		
		case(KeyEvent.VK_A):
			Main.aDown = true;
			break;
			
		case(KeyEvent.VK_W): //Going for W yolo
			Main.wDown = true;//W might be more intuitive, but depends on the kind of platformer we want
			Sound.playSound("Jump.wav");//Better place and way to call this, I know. Just making sure it works and being lazy.
			break;
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		
		//Enter Debug Screen
		case(KeyEvent.VK_F3)://Cuz Minecraft, amirite?
			if(debugPressed){
				Main.debug = false;
				debugPressed = false;
				Hud.debug = false;
			}else{
				Main.debug = true;
				debugPressed = true;
				Hud.debug = true;
			}
			break;
		
		//Pausing
		case(KeyEvent.VK_ESCAPE):
			if(pausePressed){
				Main.isPaused = false;
				pausePressed = false;
				Hud.paused = false;
			}else{
				Main.isPaused = true;
				pausePressed = true;
				Hud.paused = true;
			}
			break;
		
		case(KeyEvent.VK_D):
			Main.dDown = false;
			break;
		case(KeyEvent.VK_A):
			Main.aDown = false;
			break;
		case(KeyEvent.VK_W): // Space or W for jump?
			Main.wDown = false;//W might be more intuitive, but depends on the kind of platformer we want
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
