package com.hanfeldt.game.npc;

import com.hanfeldt.game.Entity;
import com.hanfeldt.game.Sprite;

/**
 * Adding code for NPC's, I'm just playing around, so hope I'm doing this right!
 * Sorry for ripping off your code, but it's what works >_<
 * @author David Ofeldt
 *
 */
public class Npc extends Entity{
	
	boolean isMovingLeft, isMovingRight;
	
	public Npc(Sprite s, int x, int y){
		super(s, 75, x, y);
		
		isMovingLeft = false; isMovingRight = false;
	}
	
	public void tick(){
		
	}
	
	public void draw(){
		
	}
}
