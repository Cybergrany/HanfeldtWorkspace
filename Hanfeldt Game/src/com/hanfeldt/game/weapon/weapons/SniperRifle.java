package com.hanfeldt.game.weapon.weapons;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class SniperRifle extends AmmoWeapon{
	
	private int damage;
	private boolean aiming = false;
	private int aimStart = 0;
	
	/**
	 * Get n0sc0ped n00b
	 * @param e
	 */
	public SniperRifle(EntityLiving e){
		super(e, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 6, 1, 1), 6, 12, 6, 45, 90);
		setAutomatic(false);
	}
	
	public void tick(){
		super.tick();
		
		if(Main.getGame().getListener().rightMouseDown){
			if(!aiming){
				aimStart = Main.getGame().getCamera().getX();
				aiming = true;
			}else{
				Main.getGame().getCamera().setFollowingPlayer();
				aiming = false;
			}
		}
		
		if(aiming){
			Main.getGame().getCamera().scrollX(aimStart, -150, 2);
		}
		
	}
	
	public void drawFrame(Graphics g){
		
	}
	
	public void trigger(){
		if(super.getAmmoInClip() > 0){
			
		}
	}

}
