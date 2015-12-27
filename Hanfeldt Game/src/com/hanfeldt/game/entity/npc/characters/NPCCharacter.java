package com.hanfeldt.game.entity.npc.characters;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.npc.Npc;

/**
 * Class for NPC characters, which will (when finished) contain code to allow NPC's to interact
 * in a more in-depth fashion with human controlled characters (in theory)
 * It'd be cool if they could carry weapons too(and use them)
 * Gonna stop commenting and work on that now.
 * @author ofeldt
 *
 */
public class NPCCharacter extends Npc{
	
	public  float speed = 0.35f;//Fuckin' nyooom
	private boolean followingPlayer = false;
	private int aimAccuracy = 100;	
	private int aimX, aimY;

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
		if(Main.timer(30)){
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
		getClosestEnemy();
//		if(getClosestEntity() instanceof Monster){
//			aimX = (int) getClosestEntity().getX();
//			aimY = (int) getClosestEntity().getY();
//		}
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

}
