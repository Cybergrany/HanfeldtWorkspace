package com.hanfeldt.game.weapon.weapons;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class SniperRifle extends AmmoWeapon{
	
	private int damage;
	private boolean aiming = false, aimDir;
	private int aimStart = 0, scopeSize = 10, scopeDistance = 150;
	
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
		
		if(Main.getGame().getListener().rightMouseDown && !Main.getGame().getListener().rightMouseDownLastTick){
			if(!aiming){
				aimStart = Main.getGame().getCamera().getX();
				aimDir = Main.getGame().getPlayer().getDirection();
				aiming = true;
				hasOverlay = true;
			}else{
				Main.getGame().getCamera().setFollowingPlayer();
				aiming = false;
				hasOverlay = false;
			}
		}
		
		if(aiming){
			Main.getGame().getCamera().scrollX(aimStart, aimDir ?  scopeDistance : -scopeDistance, 2);
		}
		
	}
	
	public void drawOverlay(Graphics g){
//		g.setColor(Color.BLACK);
		g.drawOval(Main.mouseX - (scopeSize / 2) , Main.mouseY - (scopeSize / 2)	, scopeSize, scopeSize);
		//TODO: Finish off
//		for(int x = 0; x < Main.WIDTH; x++){
//			for(int y= 0; y < Main.HEIGHT; y++){
//				if((x > Main.mouseX - (scopeSize / 2)
//					|| x < Main.mouseX + (scopeSize / 2))
//					&& (y > Main.mouseY - (scopeSize / 2)
//					|| y < Main.mouseY - (scopeDistance / 2))
//					&& (scopeSize * 2 < (Main.mouseX*2) + (Main.mouseY*2)))
//					g.drawLine(x, y, x, y);
//			}
//		}
	}
	
	public void trigger(){
		if(super.getAmmoInClip() > 0){
			
		}
	}

}
