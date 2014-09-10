package com.hanfeldt.game.entity.npc.characters;

import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;

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
	private int aimAccuracy = 100;

	public NPCCharacter(Sprite s, int h, int x, int y) {
		super(s, h, x, y);
	}
	
	public void tick(){
		super.tick();
		if(sprite.getWalkingAnimsLength() > 0)
		tickWalking();
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
		int closestXl = 0, closestXr = 0;
		for(int i = 0; i < npc1.size(); i++){
			Npc npc = npc1.get(i);
			if(!(npc instanceof NPCCharacter))
			if(!npc.getEvents().isOutsideMap(npc)){
				if(direction){//Facing left
					if(closestXl < getX() - npc.getX() + getX() || closestXl == 0){
						closestXl =  getX() - npc.getX();
						if(closestXl > 0){
							setAimX(npc.getX());
							setAimY(npc.getY() - 2);
//							setAimY(npc.getY() - 5 + (100 - aimAccuracy));
							if(weaponEquipped instanceof TriggerWeapon){
								((TriggerWeapon) weaponEquipped).tryTrigger();
							}
						}
					}
				}else{//Facing right
					if(closestXr < npc.getX() - getX() || closestXr == 0){
						closestXr =  getX() - npc.getX();
						if(closestXr > 0){
							setAimX(npc.getX());
							setAimY(npc.getY() - 2);
//							setAimY(npc.getY() - 5 + (100 - aimAccuracy));
							if(weaponEquipped instanceof TriggerWeapon){
								((TriggerWeapon) weaponEquipped).tryTrigger();
							}
						}
					}
				}
			}
		}
	}
	
	public void setAimAccuracy(int acc){
		aimAccuracy = acc;
	}
	
	public int getAimAccuracy(){
		return aimAccuracy;
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
