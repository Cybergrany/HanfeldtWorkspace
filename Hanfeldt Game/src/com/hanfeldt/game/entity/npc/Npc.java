package com.hanfeldt.game.entity.npc;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.event.NpcEvents;
import com.hanfeldt.game.weapon.Weapon;
import com.sun.glass.ui.CommonDialogs.Type;

/**
 *Non player controlled entities 
 * @author David Ofeldt
 *
 */
public class Npc extends EntityLiving {
	
	int health = 0;
	protected NpcEvents events;
	private static Dimension spawnLocation;
	private ArrayList<Npc> enemies;
	
	public Npc(Sprite s,int h, int x, int y){
		super(s, h, x, y);
		events = new NpcEvents(this);
		spawnLocation = new Dimension(x, y);
		enemies = new ArrayList<Npc>();
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
	
	/**
	 *Make Npc unfriendly towards other npc 
	 *TODO: player character as well 
	 */
	public void addEnemy(Npc npc){
		enemies.add(npc);
	}
	
	public ArrayList<Npc> getEnemies(){
		return enemies;
	}
	
	/**
	 * Detects if npc is friendly towards passed npc
	 * @param npc
	 * @return
	 */
	public boolean isFriendly(Npc npc){
		return !(enemies.contains(npc));//Returns the opposite, as the list is for enemies.
	}
	
	/**
	 * Get the closest enemy
	 * @param type
	 * @return
	 */
	public Point getClosestEnemy(){
		ArrayList<Npc> npcs = Main.getGame().getEntityManager().getNpcs();
		
		double shortestDist = Integer.MAX_VALUE;
		int shortestDistIndex = 0;
		
		for(int i = 0; i < npcs.size(); i++){
			Npc compareTo = npcs.get(i);
			if(compareTo.isFriendly(this) ||this == compareTo){
				continue;
			}
			int distX = Math.abs(compareTo.getX() - getX());
			int distY = Math.abs(compareTo.getY() - getY());
			
			double distance = Math.sqrt(distX * distX + distY * distY);
			
			if(distance < shortestDist){
				shortestDist = distance;
				shortestDistIndex = i;
			}
		}
		Npc closestNpc = npcs.get(shortestDistIndex);
		return new Point(closestNpc.getX() + (closestNpc.getSizeX() / 2), closestNpc.getY() + (closestNpc.getSizeY() / 2));
	}
	
}
