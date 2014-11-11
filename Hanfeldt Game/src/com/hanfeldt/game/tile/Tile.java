package com.hanfeldt.game.tile;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.InfoPopUp;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.io.Debug;
import com.hanfeldt.game.weapon.AmmoWeapon;

public class Tile {
	
	public boolean isSolid = false, isVisible = false, isAction = false,
								  isParticle = false, isTrigger = false, triggered = false;
	public String name;
	
	private InfoPopUp useTile;
	private Sprite sprite;
	private int x, y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile(int x, int y, int sx, int sy, int w, int h){
		this.x = x;
		this.y = y;
		setSprite(new Sprite(SpriteSheet.getSheet(SpriteSheet.block), sx, sy, w, h));
	}
	
	public boolean isSolid() {
		return isSolid;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public int getTileX() {
		return x;
	}
	
	public int getTileY() {
		return y;
	}
	
	public int getX() {
		return x *Main.TILE_SIZE;
	}
	
	public int getY() {
		return y *Main.TILE_SIZE;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	/**
	 * Called when an entity passes through the tile
	 * @param e
	 */
	public void onPassedThroughEntity(Entity e){
		if(e instanceof EntityLiving && isTrigger){
			if(useTile != null){
				checkTrigger((EntityLiving) e);
			}else{
				useTile = new InfoPopUp("Press e to use.", getTileX(), getTileY(), 40);
				checkTrigger((EntityLiving) e);
			}
		}
	}
	
	
	/**
	 * Called whenever an entity is resting above the tile(ie walking on it)
	 * @param e
	 */
	public void onCollidedEntityAbove(Entity e) {
		if(isParticle){
			
		}
	}
	
	public void checkTrigger(EntityLiving e){
		switch(name){
			case "Ammo":
				if(e instanceof Player /*Don't want zombies reloading for now*/ && !triggered && e.weaponEquipped != null && e.weaponEquipped instanceof AmmoWeapon){
					AmmoWeapon wep = (AmmoWeapon) e.getWeaponEquipped();
					wep.setTotalAmmo(wep.getTotalAmmo() + Values.bullets_per_crate);
					Debug.printDebug("Increased player ammo by: " + Values.bullets_per_crate);
					triggered = true;
				}
				break;
			case "layerUp":
				if(e instanceof Player && !triggered){
					useTile.draw();
					if(useTile.triggered){
						e.moveToLayerAbove();
						triggered = true;
					}
				}
				break;
			case "layerDown":
				if(e instanceof Player && !triggered){
					useTile.draw();
					if(useTile.triggered){
						e.moveToLayerBelow();
						triggered = true;
					}
				}
				break;
		}
	}
	
}
