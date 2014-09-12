package com.hanfeldt.game.entity.item;

import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.weapon.weapons.Sword;

public class SwordItem extends EntityItem{
	
	public SwordItem(int x, int y) {
		super(new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 0, 0, 1, 1), x, y);
	}
	
	public String getType(){
		return "Weapon";
	}
	
	public Object getLinkedItem(EntityLiving e){
		return(new Sword(e));
	}
}
