package com.hanfeldt.game.event.command;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.item.ItemSpawner;
import com.hanfeldt.game.entity.item.Sword;

public class SpawnCommandEvent {
	
	public SpawnCommandEvent(){
		
	}
	
	public static void checkSpawnCommand(String command){
		switch(command){
			case "sword":
				ItemSpawner.spawnItem(new Sword(Main.getGame().getPlayer().getX() + 5, Main.getGame().getPlayer().getY()));
		}
	}

}
