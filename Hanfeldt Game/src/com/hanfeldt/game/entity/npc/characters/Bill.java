package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.weapon.weapons.Pistol;

/**
 * Bill appears first 
 * @author ofeldt
 *
 */
public class Bill extends NPCCharacter {

	public Bill(int x, int y) {
		super(new Sprite(SpriteSheet.getSheet(SpriteSheet.character), 0, 0, 1, 2, 3), 100, x, y);
		setHealth(50);//Weak-ass puny human
		setWeaponEquipped(new Pistol(this));
		setVelXMax(speed);
		setJumpHeight(2.3f);//Slightly higher than block so they can jump over them with ease
	}

}
