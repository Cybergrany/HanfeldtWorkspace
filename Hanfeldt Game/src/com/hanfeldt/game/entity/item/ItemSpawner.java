package com.hanfeldt.game.entity.item;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.EntityItem;

public class ItemSpawner {
	
	public ItemSpawner(){
		
	}
	
	public static void spawnItem(EntityItem item){
//		Main.getGame().getItems().add(item);
		Main.getGame().getEntityManager().addItems(item);
	}
}
