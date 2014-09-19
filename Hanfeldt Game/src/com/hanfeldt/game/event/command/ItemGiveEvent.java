package com.hanfeldt.game.event.command;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.io.Debug;

public class ItemGiveEvent {
	
	public ItemGiveEvent(){
		
	}
	
	public static void checkGiveCommand(String command) throws CommandNotFoundException{
		switch(command){
			case "pistol":
				Main.getGame().getPlayer().setWeaponEquipped(new Pistol(Main.getGame().getPlayer()));
				Debug.printDebug("Pistol given to player.");
				break;
			case "m16":
				Main.getGame().getPlayer().setWeaponEquipped(new M16(Main.getGame().getPlayer()));
				Debug.printDebug("M16 given to player");
				break;
			default:
					throw new CommandNotFoundException();
		}
	}
}
