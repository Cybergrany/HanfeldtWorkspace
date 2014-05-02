package com.hanfeldt.game.npc;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;

public class Zombie extends Npc{
	private float speed = 0.25f;
	private static Sprite s = new Sprite(Main.spriteSheet, 2, 3, 1, 2);//Same as player for testing
	
	public static int maxHealth = 75;

	public Zombie(int x, int y) {
		super(s, maxHealth, x, y);
		setVelXMax(speed);
	}
	
	public void tick() {
		super.tick();
		if(Main.getGame().getTotalTicks() % 120 == 0) {
			facePlayer(speed);
		}
	}
	
	public static int getMaxNpc(){
		return 3;
	}

}
