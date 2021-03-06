package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.projectile.GrenadeRPG;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class RPGLauncher extends AmmoWeapon{
	
	/**
	 * Kaboom
	 */
	public RPGLauncher(EntityLiving p){
		super(p, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 4, 1, 1), 1, 3, 1, 50, 120);
		setAutomatic(true);
	}
	
	public void trigger(){
		if(super.getAmmoInClip() > 0){
			if(!entity.getDirection()){
//				Main.getGame().getLayers().get(entity.getLayer()).addBullet(new GrenadeRPG(entity, entity.getX() - 5, entity.getY() + 8, entity.getLayer()));
				Main.getGame().getEntityManager().addBullet(new GrenadeRPG(entity, entity.getX() - 5, entity.getY() + 8, entity.getLayer()));
			}else{
//				Main.getGame().getLayers().get(entity.getLayer()).addBullet(new GrenadeRPG(entity, entity.getX() + Main.TILE_SIZE + 5, entity.getY() + 8, entity.getLayer()));
				Main.getGame().getEntityManager().addBullet(new GrenadeRPG(entity, entity.getX() + Main.TILE_SIZE + 5, entity.getY() + 8, entity.getLayer()));
			}
			//TODO Add trigger sound
			super.trigger();
		}else{
			//TODO add empty clip sound
		}
	}
	
	public String name(){
		return "Rpg";
	}

}
