package com.hanfeldt.game.entity.npc;

import java.awt.Dimension;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.event.NpcEvents;
import com.hanfeldt.game.weapon.Weapon;

/**
 * Adding code for NPC's, I'm just playing around, so hope I'm doing this right!
 * Sorry for ripping off your code, but it's what works >_<
 * @author David Ofeldt
 *
 */
public class Npc extends EntityLiving {
	
	int health = 0;
	protected NpcEvents events;
	private static Dimension spawnLocation;
	
	public Npc(Sprite s,int h, int x, int y){
		super(s, h, x, y);
		events = new NpcEvents(this);
		spawnLocation = new Dimension(x, y);
	}

	public void tick(){
		super.tick();
	}
	
	/**
	 * Max amount of NPC's allowed.
	 * Can be overridden etc(I believe)
	 * @return A default of 5.
	 */
	public static int getMaxNpc(){
		return Values.max_npc_default;//Default max
	}
	
	public void facePlayer(float moveSpeed) {
		setDirection(Main.getGame().getPlayer().getX() < getX());
		if(Main.getGame().getPlayer().getX() < getX()) {
			setVelX(-moveSpeed);
		}else{
			setVelX(moveSpeed);
		}
	}
	
	public NpcEvents getNpcvents() {
		return events;
	}
	
	public Dimension getSpawnLocation(){
		return spawnLocation;
	}
	
	public NpcEvents getEvents(){
		return events;
	}
	
	public void setWeaponEquipped(Weapon wep) {
		weaponEquipped = wep;;
	}
	
}
