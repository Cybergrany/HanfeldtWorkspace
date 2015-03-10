package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.WeaponSwung;

/**
 * Currently just a placeholder to avoid nullpointers when no weapon is selected
 * Could be used for punching + carrying items at some stage
 * @author ofeldt
 *
 */
public class Hands extends WeaponSwung{
	
	private static final int range = 0;
	private static final int damage = 0;
	private static final Sprite idleSprite = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 9, 9, 1, 1);//Empty cell
	
	public Hands(EntityLiving e){
		super(e, idleSprite, idleSprite, range, damage, 25);
	}
	
	public String name(){
		return "hands";
	}
}
