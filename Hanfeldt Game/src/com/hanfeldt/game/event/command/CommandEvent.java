package com.hanfeldt.game.event.command;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.item.ItemSpawner;
import com.hanfeldt.game.entity.item.SwordItem;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.entity.npc.characters.NPCCharacter;
import com.hanfeldt.game.entity.npc.monsters.Zombie;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.io.Debug;


public class CommandEvent {
	
	public CommandEvent(){
		ConsoleHandler handler = new ConsoleHandler();
		handler.start();
	}
	
	/**
	 * Checks a command and executes it if it matches the list of available commands
	 * Please note that two word commands are only supported for the time being.
	 * @param command
	 */
	public static void checkCommand(String command) throws CommandNotFoundException{
		command = command.toLowerCase();
		String[] commandAr = command.split("\\s+");
		String subcommand;
		if(commandAr.length > 1){
			command = commandAr[0];
			subcommand = commandAr[1];
		}else{
			subcommand = commandAr[0];
		}
		switch(command){
			case "give":
				checkGiveCommand(subcommand);
				break;
			case "set":
				checkSetCommand(subcommand);
				break;
			case "spawn":
				checkSpawnCommand(subcommand);
				break;
			case "drop":
				checkDropCommand(subcommand);
				break;
			case "printFPS":
				Main.printFPS = !Main.printFPS;
				break;
			case "zoom":
				checkSetZoomCommand(subcommand);
				break;
			default:
				throw new CommandNotFoundException(subcommand);
		}
//		throw new CommandNotFoundException();
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
					throw new CommandNotFoundException(command);
		}
	}

	public static void checkSetCommand(String command) throws CommandNotFoundException{
		switch(command){
			case "npc_following":
				for(int i = 0; i < Main.getGame().getNpc().size(); i++){
					Npc npc = Main.getGame().getNpc().get(i);
					if(npc instanceof NPCCharacter){
						((NPCCharacter) npc).setFollowingPlayer(true);
					}
				}
				break;
			default:
				throw new CommandNotFoundException(command);
		}
	}

	public static void checkSpawnCommand(String command) throws CommandNotFoundException{
		switch(command){
			case "sword":
				Debug.printDebug("Spawned Sword at: " + (Main.getGame().getPlayer().getX() + 5) + "\t" + Main.getGame().getPlayer().getY() );
				ItemSpawner.spawnItem(new SwordItem(Main.getGame().getPlayer().getX() + 5, Main.getGame().getPlayer().getY()));
				break;
			case "zombie":
				Spawner.spawnNpc(new Zombie(Main.getGame().getPlayer().getX() + Main.TILE_SIZE * 3, Main.getGame().getPlayer().getY() - Main.TILE_SIZE * 2));
				break;
			default:
				Main.getGame().getPlayer().setLocation(Main.getGame().getLevels().getPlayerSpawnPoint());
		}
	}
	
	public static void checkDropCommand(String command)throws CommandNotFoundException{
		switch(command){
			case "hand":
				Debug.printDebug("Hand item dropped");
				EntityItem ei = new EntityItem(Main.getGame().getPlayer().getWeaponEquipped().getSprite(), Main.getGame().getPlayer().getX(), Main.getGame().getPlayer().getY());
				ei.setLinkedItem(Main.getGame().getPlayer().getWeaponEquipped());//Added this so zombies can pick up any weapon
				ItemSpawner.spawnItem(ei);
				break;
				default:
					throw new CommandNotFoundException(command);
		}
	}
	
	public static void checkSetZoomCommand(String command) throws CommandNotFoundException{
		try{
			int z = Integer.parseInt(command);
			Main.getGame().getCamera().setZoom(z);
		}catch(Exception e){
			throw new CommandNotFoundException();
		}
	}
}
