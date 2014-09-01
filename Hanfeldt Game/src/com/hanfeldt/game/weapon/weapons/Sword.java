package com.hanfeldt.game.weapon.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.event.NpcEvents;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.io.Sound;

public class Sword extends TriggerWeapon {
	private static final int range = 16;
	private static final int damage = 50;
	private static final Sprite idleSprite = new Sprite(Main.getSpritesheet(), 4, 3, 1, 1);
	
	/**
	 * Swashbuckling adventure awaits
	 * @param p
	 */
	public Sword(Player p) {
		super(p, new Sprite(Main.getSpritesheet(), 3, 3, 1, 1), 20);
	}
	
	public void draw(Graphics g) {//#Im14AndThisIsFunny <-- LELELEL /r/SubredditsAreHashtags
		if(tickTriggered > 0) { // Penis out straight
			if(!player.getDirection()) {
				sprite.draw(g, (Main.WIDTH /2) - Main.TILE_SIZE -2, player.getY() + (Main.TILE_SIZE /2), player.getDirection());
			}else{
				sprite.draw(g, (Main.WIDTH /2) +2, player.getY() + (Main.TILE_SIZE /2), player.getDirection());
			}
		}else{// Hold penis upright LELEL
			if(!player.getDirection()) {
				idleSprite.draw(g, (Main.WIDTH /2) - 13, player.getY() + 2, player.getDirection());
			}else{
				idleSprite.draw(g, (Main.WIDTH /2) - 3, player.getY() +2, player.getDirection());
			}
		}
	}
	
	protected void trigger() {
		ArrayList<Npc> npcs = Main.getGame().getNpc();
		Rectangle hitBoxLeft = new Rectangle(player.getX() - range, player.getY(), range, range *2);
		Rectangle hitBoxRight= new Rectangle(player.getX() + range, player.getY(), range, range *2);
		for(int i=0; i<npcs.size(); i++) {
			NpcEvents e = npcs.get(i).getNpcvents();
			if(!player.getDirection()) {
				if(hitBoxLeft.intersects(npcs.get(i).getBounds())) {
					e.damageNpc(npcs.get(i), damage, Values.zombie_damage_from_sword_id);
					Main.getGame().addGore(hitBoxLeft.x, hitBoxLeft.y);
				}
			}else{
				if(hitBoxRight.intersects(npcs.get(i).getBounds())) {
					e.damageNpc(npcs.get(i), damage, Values.zombie_damage_from_sword_id);
					Main.getGame().addGore(hitBoxRight.x, hitBoxRight.y);
				}
			}
		}
		Sound.playSound("Sword_Swing.wav");
	}
	
	public Sprite getNotTriggeredSprite() {
		return new Sprite(Main.getSpritesheet().getImage(4, 3, 1, 1));
	}
	
}
