package com.hanfeldt.game.weapon;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.event.NpcEvents;
import com.hanfeldt.io.Debug;
import com.hanfeldt.io.Sound;

public class WeaponSwung extends TriggerWeapon{
	public int range = 16;
	public int damage = 50;
	public Sprite idleSprite;
	private String sound = "Sword_Swing.wav";//Default
	
	/**
	 * Swoosh
	 */
	public WeaponSwung(EntityLiving e, Sprite idleSprite, Sprite mainSprite, int range, int damage, int swingTime){
		super(e, mainSprite, swingTime);
		this.range = range;
		this.damage = damage;
		this.idleSprite = idleSprite;
		setAutomatic(false);//Non-automatic by default
	}
	
	protected void trigger() {
		ArrayList<Npc> npcs = Main.getGame().getNpc();
		Rectangle hitBoxLeft = new Rectangle(entity.getX() - range, entity.getY(), range, range *2);
		Rectangle hitBoxRight= new Rectangle(entity.getX() + range, entity.getY(), range, range *2);
		for(int i=0; i<npcs.size(); i++) {
			NpcEvents e = npcs.get(i).getNpcvents();
			if(!entity.getDirection()) {
				if(hitBoxLeft.intersects(npcs.get(i).getBounds())) {
					e.damageNpc(npcs.get(i), damage, NpcEvents.zombie_damage_from_sword_id);
					try{//Try/catching because killing npc's causes an indexoutofboundsexception.TODO fix
						Main.getGame().addGore(hitBoxLeft.x, hitBoxLeft.y);
					}catch(Exception exception){
						Debug.printErrorDebug("Error while adding gore, see WeaponSwung.java:42");
					}
				}
			}else{
				if(hitBoxRight.intersects(npcs.get(i).getBounds())) {
					e.damageNpc(npcs.get(i), damage, NpcEvents.zombie_damage_from_sword_id);
					Main.getGame().addGore(hitBoxRight.x, hitBoxRight.y);
				}
			}
		}
		Sound.playSound(sound);
	}
	
	public Sprite getNotTriggeredSprite() {
		return idleSprite;
	}
	
	public void setSwingSound(String s){
		sound = s;
	}
}
