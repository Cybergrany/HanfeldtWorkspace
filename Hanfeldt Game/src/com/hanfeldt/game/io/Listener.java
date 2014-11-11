package com.hanfeldt.game.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Hud;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.state.menus.PauseMenuState;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
	
	private boolean pausePressed, debugPressed;
	public boolean aDown, dDown, wDown, eDown, escDown,
	shiftDown, mouseDown, mouseDownLastTick,
	rightMouseDown, rightMouseDownLastTick,
	upArrowDown, downArrowDown, enterDown,
	shopButtonDown, shopButtonDownLastTick,
	spaceDown, spaceDownLastTick;
	
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
			
		case(KeyEvent.VK_E):
			eDown=true;
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
			
		case(KeyEvent.VK_SPACE):
			spaceDown = true;
			break;
			
		case(KeyEvent.VK_SHIFT):
			shiftDown = true;
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
				pausePressed = false;
				Main.getGame().returnToPlaying();
			}else{
				pausePressed = true;
				Main.getGame().setState(new PauseMenuState(Main.getGame()));
			}
			break;
			
		//Muting
		case(KeyEvent.VK_M):
			MenuScreenOptionAction.muteGame();
			break;
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
		case(KeyEvent.VK_E):
			eDown = false;
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
		case(KeyEvent.VK_SHIFT):
			shiftDown = false;
			break;
		// Shop
		case(KeyEvent.VK_K):// 'K' seems like a good shop button to me, what do you think? 
			shopButtonDown = false;
			break;
			
			//Dialogue skip
		case(KeyEvent.VK_SPACE):
			spaceDown = false;
			break;
			
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			mouseDown = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightMouseDown = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			mouseDown = false;
		}
		if(e.getButton()  == MouseEvent.BUTTON3){
			rightMouseDown = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Main.mouseX = e.getX() /Main.SCALE;
		Main.mouseY = e.getY() /Main.SCALE;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Main.mouseX = e.getX() /Main.SCALE;
		Main.mouseY = e.getY() /Main.SCALE;
	}

}
