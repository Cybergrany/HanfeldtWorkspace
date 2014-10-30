package com.hanfeldt.game.event;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.io.Sound;

/**
 * Made this class so Player.java doesn't get too messy. I know entity.java covers a lot of this, but we'd need something
 * specific for damage sounds and animations(I think - plz correct if wrong!)
 * @author David Ofeldt
 *
 */
public class PlayerEvents{
	
	public static final int fall_damage_id = 1;
	public static final int fall_death_id = 3;
	
	private Player player;
	
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
		if(Main.debugCheats) {
			return;
		}
		player.changeHealth(-damage);
		player.changestamina(-(damage / 10)); 
		playerDamage(id);
	}
	
	public synchronized void damagePlayer(int damage, int id, Npc npc) {
		if(Main.debugCheats) {
			return;
		}
		if(lastTickHurt == 0) {
			lastTickHurt = Main.getGame().getTotalTicks() - hurtTime;
		}
		if(Main.getGame().getTotalTicks() >= lastTickHurt + hurtTime) {
			lastTickHurt = Main.getGame().getTotalTicks();
			player.changeHealth(-damage);
			player.changestamina(-(damage / 2)); 
			if(player.getX() > npc.getX()) {
				player.setVelX(6);
			}else{
				player.setVelX(-6);
			}
			Main.getGame().getCamera().shake();
			//TODO: Add blood dim to screen
//			Main.getGame().getCamera().drawBlood();
			Main.getGame().addGore(player.getX(), player.getY());
			player.setVelY(-1);
			playerDamage(id);
		}
	}
	
	public void playerDamage(int id){
		if(id == NpcEvents.fall_damage_id) {
			Sound.playSound("FallDamage.wav");
		}
		if(id == NpcEvents.zombie_damage_id) {
			Sound.playSound("Hit.wav");
		}
		if(player.alive && player.getHealth() <= 0) {
			Main.getGame().playerDied();
			playerDeath(id);
		}
	}
	
	public void playerDeath(int id){
		//TODO: Different death id's, for example zombie bite, fall damage; so different animations and sounds can be called
		if(id == NpcEvents.fall_death_id){//Falling out of map
			Sound.playSound("FallDeath.wav");
		}
		if(id == NpcEvents.zombie_damage_id){
			Sound.playSound("death_from_zombie.wav");
		}
	}
	
}
