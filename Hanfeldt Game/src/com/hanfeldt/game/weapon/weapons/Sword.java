package com.hanfeldt.game.weapon.weapons;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.WeaponSwung;

public class Sword extends WeaponSwung {
	private static final int range = 16;
	private static final int damage = 50;
	private static final Sprite idleSprite = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 0, 1, 1);
	
	/**
	 * Swashbuckling adventure awaits
	 * @param p
	 */
	public Sword(EntityLiving p) {
		super(p, idleSprite, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 0, 1, 1), range, damage, 20);
		setSwingSound("Sword_Swing.wav");
	}
	
	@Deprecated
	public void draw(Graphics g) {//#Im14AndThisIsFunny <-- LELELEL /r/SubredditsAreHashtags
		if(tickTriggered > 0) { // Penis out straight
			if(!entity.getDirection()) {
				sprite.draw(g, (Main.WIDTH /2) - Main.TILE_SIZE -2, entity.getY() + (Main.TILE_SIZE /2), entity.getDirection());
			}else{
				sprite.draw(g, (Main.WIDTH /2) +2, entity.getY() + (Main.TILE_SIZE /2), entity.getDirection());
			}
		}else{// Hold penis upright LELEL
			if(!entity.getDirection()) {
				idleSprite.draw(g, (Main.WIDTH /2) - 13, entity.getY() + 2, entity.getDirection());
			}else{
				idleSprite.draw(g, (Main.WIDTH /2) - 3, entity.getY() +2, entity.getDirection());
			}
		}
	}
	
}
