package com.hanfeldt.game.event;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sound;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;

/**
 * Made this class so Player.java doesn't get too messy. I know entity.java covers a lot of this, but we'd need something
 * specific for damage sounds and animations(I think - plz correct if wrong!)
 * @author David Ofeldt
 *
 */
public class PlayerEvents{
	
	private Player player;
	
	public static int fallDamage = 1;//Not sure if this is best way to do this.
	public static int zombieDamage = 2;
	public static int fallDeath = 3;
	
	public static int zombieDamageDealt = 10;
	
	public static int zombieDeath = 2;
	
	private static final int hurtTime = 60; //How long you're invincible for after being hit
	private long lastTickHurt = 0;

	public PlayerEvents(Player player) {
		this.player = player;
	}
	
	public void tick(){
		
	}
	
	public synchronized void damagePlayer(int damage, int id) {
		player.changeHealth(-damage);
		playerDamage(id);
	}
	
	public synchronized void damagePlayer(int damage, int id, Npc npc) {
		if(lastTickHurt == 0) {
			lastTickHurt = Main.getGame().getTotalTicks() - hurtTime;
		}
		if(Main.getGame().getTotalTicks() >= lastTickHurt + hurtTime) {
			lastTickHurt = Main.getGame().getTotalTicks();
			player.changeHealth(-damage);
			if(player.getX() > npc.getX()) {
				player.setVelX(6);
			}else{
				player.setVelX(-6);
			}
			player.setVelY(-1);
			playerDamage(id);
		}
	}
	
	public void playerDamage(int id){
		if(id == fallDamage) {
			Sound.playSound("FallDamage.wav");
		}
		if(id == zombieDamage) {
			Sound.playSound("Hit.wav");
		}
		if(id == fallDeath) {
			playerDeath(fallDeath);
		}
		if(player.alive && player.getHealth() <= 0) {
			Main.getGame().playerDied();
		}
	}
	
	public void playerDeath(int id){
		//TODO: Different death id's, for example zombie bite, fall damage; so different animations and sounds can be called
		if(id == fallDeath){//Falling out of map
			Sound.playSound("FallDeath.wav");
		}
	}
	
}
