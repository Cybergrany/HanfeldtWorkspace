package com.hanfeldt.game.weapon.weapons;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.entity.projectile.BulletNpcFired;
import com.hanfeldt.game.io.Sound;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class Pistol extends AmmoWeapon {
	private int damage;
	
	/**
	 * Pew-Pew-Pew
	 * 
	 * @param p A {@link Player} Sprite
	 * @param aic 
	 * @param ta
	 */
	public Pistol(EntityLiving p) {
		super(p, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 1, 1, 1), 8, 64, 8, 10, 50);
		damage = Values.bullet_damage_pistol;
		setAutomatic(false);
	}
	
	public void trigger() {
		if(super.getAmmoInClip() > 0){//Trigger only with bullets in clip
			if(!entity.getDirection()) {
				// facing left
				if(entity instanceof Player){
//					Main.getGame().bullets.add(new Bullet(entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
					Main.getGame().getLayers().get(entity.getLayer()).addBullet(new Bullet(entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
				}else{
//					System.out.println(entity.getAimX() + "\t" + entity.getAimY() + "\tDelete from Pistol.java");
//					Main.getGame().bullets.add(new BulletNpcFired(entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getAimX() + Main.TILE_SIZE, entity.getAimY() + Main.TILE_SIZE, entity));
					Main.getGame().getLayers().get(entity.getLayer()).addBullet(new BulletNpcFired(entity.getX() - 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getAimX() + Main.TILE_SIZE, entity.getAimY() + Main.TILE_SIZE, entity));
				}
			}else{
				// facing right
				if(entity instanceof Player){
//					Main.getGame().bullets.add(new Bullet(entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
					Main.getGame().getLayers().get(entity.getLayer()).addBullet(new Bullet(entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getLayer()));
				}else{
//					System.out.println(entity.getAimX() + "\t" + entity.getAimY());
//					Main.getGame().bullets.add(new BulletNpcFired(entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getAimX() + Main.TILE_SIZE, entity.getAimY() + Main.TILE_SIZE, entity));
					Main.getGame().getLayers().get(entity.getLayer()).addBullet(new BulletNpcFired(entity.getX() + Main.TILE_SIZE + 5, entity.getY() + Main.TILE_SIZE -2, damage, entity.getAimX() + Main.TILE_SIZE, entity.getAimY() + Main.TILE_SIZE, entity));
				}
			}
			Sound.playSound("weapon/pistol_shoot.wav");
			super.trigger();
		}else{
			Sound.playSound("weapon/pistol_empty.wav");
		}
	}	
}
