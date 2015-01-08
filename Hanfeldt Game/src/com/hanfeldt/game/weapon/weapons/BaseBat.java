package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.WeaponSwung;

public class BaseBat extends WeaponSwung{
	private static final int range = 16;
	private static final int damage = 30;
	private static final Sprite idleSprite = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 5, 1, 1);
	
	/***
	 * A baseball bat
	 * The standard weapon of choice for every zombie survivalist.
	 * Used by both new and old survivors, this trusty swung weapon is guaranteed to be in every zombie game/movie/book/film ever
	 */
	public BaseBat(EntityLiving e){
		super(e, idleSprite, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 5, 1 , 1), range, damage, 25);
		//TODO add swing sound
	}
	
	public String name(){
		return "BaseBat";
	}
}
