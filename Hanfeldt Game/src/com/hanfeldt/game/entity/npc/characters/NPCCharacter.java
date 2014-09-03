package com.hanfeldt.game.entity.npc.characters;

import java.util.ArrayList;

import com.hanfeldt.game.Main;
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

	public  float speed = 0.35f;//Fuckin' nyooom
	
	private boolean followingPlayer = false;

	public NPCCharacter(Sprite s, int h, int x, int y) {
		super(s, h, x, y);
	}
	
	public void tick(){
		super.tick();
		if(followingPlayer){
			followPlayer();
		}
	}
	
	public void followPlayer(){
		if(Main.timer(60)){
			if(isCollidingWithHorizTile() && !getFalling()){
				jump();
				isCollidingWithHorizTile = false;
			}else{
				facePlayer(speed);
				aim();
			}
		}
	}
	
	/**
	 * Aims at baddies
	 */
	public void aim(){
		ArrayList<Npc> npc1 = Main.getGame().getNpc();
		int closestXf = 0, closestXc = 0;
		for(int i = 0; i < npc1.size(); i++){
			Npc npc = npc1.get(i);
			if(!(npc instanceof NPCCharacter))
			if(!npc.getEvents().isOutsideMap(npc)){
				if(!direction){//Facing left
					if(closestXf <= 0 || closestXf < getX() - npc.getX() && getX() - npc.getX() > 0){
						closestXf =  getX() - npc.getX();
						System.out.println(closestXf);
					}
				}
			}
		}
	}
	
	public void setFollowingPlayer(boolean following){
		followingPlayer = following;
	}
	
	public Weapon getWeaponEquipped() {
		return weaponEquipped;
	}
	
	public void setWeaponEquipped(Weapon wep) {
		weaponEquipped = wep;;
	}

}
