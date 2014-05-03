package com.hanfeldt.game.events;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Player;
import com.hanfeldt.game.Sound;
import com.hanfeldt.game.Sprite;

/**
 * Made this class so Player.java doesn't get too messy. I know entity.java covers a lot of this, but we'd need something
 * specific for damage sounds and animations(I think - plz correct if wrong!)
 * @author David Ofeldt
 *
 */
public class PlayerEvents{
	
	private Player player;
	private boolean ticking;
	
	public int fallDamage = 1;//Not sure if this is best way to do this.
	public int zombieDamage = 2;
	
	public int zombieDamageDealt = 10;
	
	public int zombieDeath = 2;
	
	private static final int hurtTime = 60; //How long you're invincible for after being hit
	private long lastTickHurt = 0;

	public PlayerEvents(Player player) {
		this.player = player;
		ticking = true;
	}
	
	public void tick(){
		if(player.getHealth() <= 0){
			player.alive = false;
		}
	}
	
	public synchronized void damagePlayer(int damage, int id) {
		if(id == zombieDamage) {
			if(lastTickHurt == 0) {
				lastTickHurt = Main.getGame().getTotalTicks() - hurtTime;
			}
			if(Main.getGame().getTotalTicks() >= lastTickHurt + hurtTime) {
				lastTickHurt = Main.getGame().getTotalTicks();
				player.changeHealth(-damage);
				// TODO hit away from zombie
				playerDamage(id);
			}
		}else{
			player.changeHealth(-damage);
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
		while(ticking){
			if(player.getHealth() <= 0){
				playerDeath(id);
				ticking = false;
			}
			break;
		}
	}
	
	public void playerDeath(int id){
		//TODO: Different death id's, for example zombie bite, fall damage; so different animations and sounds can be called
		Main.gameOver = true;
		if(id == 1){//Falling out of map
			Sound.playSound("FallDeath.wav");
			
		}
	}
	
}
