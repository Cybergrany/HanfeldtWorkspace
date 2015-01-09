package com.hanfeldt.game.display;

import java.util.ArrayList;

import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.weapon.Weapon;

public class Inventory {
	
	public ArrayList<InventorySprite> weaponSprites;
	
	public Inventory(){
		weaponSprites = new ArrayList<InventorySprite>();
	}
	
	public void addItem(EntityItem item){
		if(item.getLinkedItem() instanceof Weapon){
			weaponSprites.add(new InventorySprite(item.sprite, ((Weapon)item.getLinkedItem()).name()));
		}
	}
	
	public void removeItem(EntityItem item){
		if(item.getLinkedItem() instanceof Weapon){
			for(InventorySprite i : weaponSprites){
				if(i.getName() == ((Weapon)item.getLinkedItem()).name()){
					weaponSprites.remove(i);
					break;
				}
			}
		}
	}
	
}
