package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Sword;

/**
 * Class for NPC characters, which will (when finished) contain code to allow NPC's to interact
 * in a more in-depth fashion with human controlled characters (in theory)
 * It'd be cool if they could carry weapons too(and use them)
 * Gonna stop commenting and work on that now.
 * @author ofeldt
 *
 */
public class NPCCharacter extends Npc{
	
	private Weapon weaponEquipped;
	
	boolean followingPlayer = false;

	public NPCCharacter(Sprite s, int h, int x, int y) {
		super(s, h, x, y);
	}
	
	public void tick(){
		
	}
	
	public void followPlayer(){
		
	}
	
	public void setFollowingPlayer(boolean following){
		followingPlayer = following;
	}
	
	public void pickupWeapon(Weapon weapon) throws InstantiationException, IllegalAccessException{
		//Sigh I need a better way to do this.....
		if(weapon instanceof Sword){
			weaponEquipped = new Sword(this);
		}
	}

}
