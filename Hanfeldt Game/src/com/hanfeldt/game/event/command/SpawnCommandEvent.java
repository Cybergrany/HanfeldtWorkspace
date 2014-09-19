package com.hanfeldt.game.event.command;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.item.ItemSpawner;
import com.hanfeldt.game.entity.item.SwordItem;
import com.hanfeldt.io.Debug;

public class SpawnCommandEvent {
	
	public SpawnCommandEvent(){
		
	}
	
	public static void checkSpawnCommand(String command) throws CommandNotFoundException{
		switch(command){
			case "sword":
				Debug.printDebug("Spawned Sword at: " + (Main.getGame().getPlayer().getX() + 5) + "\t" + Main.getGame().getPlayer().getY() );
				ItemSpawner.spawnItem(new SwordItem(Main.getGame().getPlayer().getX() + 5, Main.getGame().getPlayer().getY()));
				break;
			default:
				Main.getGame().getPlayer().setLocation(Main.getGame().getLevels().getPlayerSpawnPoint());
		}
	}

}
