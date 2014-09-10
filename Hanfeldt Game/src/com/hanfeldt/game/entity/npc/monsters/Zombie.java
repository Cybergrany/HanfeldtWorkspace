package com.hanfeldt.game.entity.npc.monsters;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.npc.Npc;

public class Zombie extends Npc {
	private float speed = 0.25f;
//	private static Sprite s = new Sprite(Main.spriteSheet, 2, 3, 1, 2);
	private static Sprite s = new Sprite(SpriteSheet.getSheet(SpriteSheet.monster), 0, 0, 1 , 2, 3);
	
	
	public static int maxHealth = Values.zombie_max_health;

	public Zombie(int x, int y) {
		super(s, maxHealth, x, y);
		setVelXMax(speed);
		setJumpHeight(2.3f);//Slightly higher than block so they can jump over them with ease
	}
	
	public void tick() {
		super.tick();
		tickWalking();
		if(Main.timer(120)) {
			facePlayer(speed);
		}
		if(Main.timer(60)){//Checks if zombie needs to jump more often so they don't stay still as much
			if(isCollidingWithHorizTile() && !getFalling()){
				jump();
				isCollidingWithHorizTile = false;
			}if(events.isOutsideScreen(this)){
				events.idle(this, speed);
			}else{
				facePlayer(speed);//Forward momentum towards player
			}
		}
		events.tick();
	}
	
	public static int getMaxNpc(){
		return Values.max_zombie;
	}
}
