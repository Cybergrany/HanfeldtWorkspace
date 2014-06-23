package com.hanfeldt.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.hanfeldt.game.Hud;
import com.hanfeldt.game.Main;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
	
	private boolean pausePressed, debugPressed, mutePressed;
	public boolean aDown, dDown, wDown, escDown,
	mouseDown, mouseDownLastTick,
	upArrowDown, downArrowDown, enterDown,
	shopButtonDown, shopButtonDownLastTick;
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
			
		case(KeyEvent.VK_D):
			dDown = true;
			break;
		
		case(KeyEvent.VK_A):
			aDown = true;
			break;
			
		case(KeyEvent.VK_W): //Going for W yolo
			wDown = true;
			break;
			
		case(KeyEvent.VK_UP):
			upArrowDown = true;
			break;
			
		case(KeyEvent.VK_DOWN):
			downArrowDown = true;
			break;
			
		case(KeyEvent.VK_ENTER):
			enterDown = true;
			break;
		
		case(KeyEvent.VK_K):// 'K' seems like a good shop button to me, what do you think? 
			shopButtonDown = true;
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
		//Reloading
		case(KeyEvent.VK_R):
			Main.getGame().reload();
			break;
		case(KeyEvent.VK_D):
			dDown = false;
			break;
		case(KeyEvent.VK_A):
			aDown = false;
			break;
		case(KeyEvent.VK_W):
			wDown = false;
			break;
		case(KeyEvent.VK_UP):
			upArrowDown = false;
			break;
		case(KeyEvent.VK_DOWN):
			downArrowDown = false;
			break;
		case(KeyEvent.VK_ENTER):
			enterDown = false;
			break;
		// Shop
		case(KeyEvent.VK_K):// 'K' seems like a good shop button to me, what do you think? 
			shopButtonDown = false;
			break;
		
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
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
