package com.hanfeldt.game.event;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;

public class ItemGiveEvent {
	
	public ItemGiveEvent(){
		
	}
	
	public static void checkGiveCommand(String command){
		command.toLowerCase();
		System.err.println(command);
		switch(command){
			case "pistol":
				Main.getGame().getPlayer().setWeaponEquipped(new Pistol(Main.getGame().getPlayer()));
				System.out.println("Pistol given");
				break;
			case "m16":
				Main.getGame().getPlayer().setWeaponEquipped(new M16(Main.getGame().getPlayer()));
				System.out.println("m16 given");
				break;
		}
	}
}
