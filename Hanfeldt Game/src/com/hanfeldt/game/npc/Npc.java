package com.hanfeldt.game.npc;

import java.awt.Graphics;

import com.hanfeldt.game.Entity;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

/**
 * Adding code for NPC's, I'm just playing around, so hope I'm doing this right!
 * Sorry for ripping off your code, but it's what works >_<
 * @author David Ofeldt
 *
 */
public class Npc extends Entity{
	
	public boolean isMovingLeft, isMovingRight;
	int temp = (Main.sizeX /2) - (Main.tileSize /2);
	
	int health = 0;
	
	public Npc(Sprite s,int h, int x, int y){
		super(s, h, x, y);
		isMovingLeft = false; isMovingRight = false;
	}

	public void tick(){
		//TODO:This probably doesn't need overriding
		
	}
	
	public void tickNpc(){
		temp++;
	}
	
	public void draw(Graphics g){
		sprite.draw(g, temp, getY());
	}
	
	/**
	 * Max amount of NPC's allowed.
	 * Can be overridden etc(I believe)
	 * @return A default of 5.
	 */
	public static int getMaxNpc(){
		return 5;//Default max
	}
}
