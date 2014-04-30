package com.hanfeldt.game.npc;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class Zombie extends Npc{
	
	private static Sprite s = new Sprite(Main.spriteSheet, 2, 1, 1, 2, 3);//Same as player for testing
	
	public static int maxHealth = 75;

	public Zombie(int x, int y) {
		super(s, maxHealth, x, y);
		
	}
	
	public void draw(){
		
	}
	
	public void tick(){
		
	}
	
	public static int getMaxNpc(){
		return 3;
	}
	
	private void detectPlayer(){//Zombie see, zombie kill
		
	}

}
