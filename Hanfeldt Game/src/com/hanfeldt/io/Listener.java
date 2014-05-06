package com.hanfeldt.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.hanfeldt.game.Hud;
import com.hanfeldt.game.Main;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
	
	static boolean pausePressed, debugPressed, mutePressed, soundPlayed;

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
			if(!soundPlayed){
				Sound.playSound("Jump.wav");//Better place and way to call this, I know. Just making sure it works and being lazy.
			}
			soundPlayed = true;
			break;
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		
		//Enter Debug Screen
		case(KeyEvent.VK_F3)://Cuz Minecraft, amirite? //Absolutely!
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
			
		//Muting
		case(KeyEvent.VK_M):
			if(mutePressed){
				Main.muted = false;
				mutePressed = false;
				Hud.muted = false;
			}else{
				Main.muted = true;
				mutePressed = true;
				Hud.muted = true;
			}
		
		case(KeyEvent.VK_D):
			Main.dDown = false;
			break;
		case(KeyEvent.VK_A):
			Main.aDown = false;
			break;
		case(KeyEvent.VK_W):
			Main.wDown = false;//W is good :)
			soundPlayed = false;
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
		Main.mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Main.mouseDown = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Main.mouseX = e.getX() /Main.scale;
		Main.mouseY = e.getY() /Main.scale;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Main.mouseX = e.getX() /Main.scale;
		Main.mouseY = e.getY() /Main.scale;
	}

}
