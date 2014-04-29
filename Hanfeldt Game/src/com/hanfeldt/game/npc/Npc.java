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
	
	public Npc(Sprite s, int x, int y){
		super(s, 75, x, y);
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
	
	public static int getMaxNpc(){
		return 5;//Default max
	}
}
