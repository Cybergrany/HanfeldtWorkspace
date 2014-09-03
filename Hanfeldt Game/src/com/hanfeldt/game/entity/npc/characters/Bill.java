package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.weapon.weapons.M16;

/**
 * Bill appears first 
 * @author ofeldt
 *
 */
public class Bill extends NPCCharacter {

	public Bill(int x, int y) {
		super(new Sprite(Main.getSpritesheet(), 6, 0, 1, 2), 100, x, y);
		setHealth(50);//Weak-ass puny human
		setWeaponEquipped(new M16(this));
		setFollowingPlayer(true);
		setVelXMax(speed);
		setJumpHeight(2.3f);//Slightly higher than block so they can jump over them with ease
	}

}
