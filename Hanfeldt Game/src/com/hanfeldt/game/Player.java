package com.hanfeldt.game;

public class Player extends Entity {
	
	//TODO: Smooth out movement
	public static int movement = 0;
	
	public static int x, y = 95;
	public static int dir = 0;
	
	static int relativeX = 0;
	
	public Player(){
		
	}
	
	public static void tick(){
		if(x > Main.sizeX - 90){
			x--;
			relativeX++;
		}else{
			x = movement - relativeX;
		}
	}
	
	public void draw(){
		
	}
}
