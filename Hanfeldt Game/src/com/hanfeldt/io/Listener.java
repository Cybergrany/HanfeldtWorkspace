package com.hanfeldt.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.hanfeldt.game.Hud;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.MenuScreen;

public class Listener implements MouseListener, KeyListener, MouseMotionListener {
	
	private boolean pausePressed, debugPressed, mutePressed;
	public boolean aDown, dDown, wDown, escDown, mouseDown, mouseDownLastTick;
	
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
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		
		//Splash
		case(KeyEvent.VK_UP):
			if(Main.splashShowing){
				if(MenuScreen.getOptionSelected() - 1 < 0){
					MenuScreen.setOptionSelected(MenuScreen.getOptionAmount() - 1);
				}else{
					MenuScreen.setOptionSelected(MenuScreen.getOptionSelected() - 1);
				}
			}
			break;
		case(KeyEvent.VK_DOWN):
			if(Main.splashShowing){
				if(MenuScreen.getOptionSelected() + 1 > MenuScreen.getOptionAmount() - 1){
					MenuScreen.setOptionSelected(0);
				}else{
					MenuScreen.setOptionSelected(MenuScreen.getOptionSelected() + 1);
				}
			}
			break;
		case(KeyEvent.VK_ENTER):
			if(Main.splashShowing){
				MenuScreen.setOptionChosen(MenuScreen.getOptionSelected());
			}
			break;
		
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
		//Hippity hoppity skilly reloading!
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
		mouseDownLastTick = mouseDown;
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
