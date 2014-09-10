package com.hanfeldt.game.event.command;

public class CommandEvent {
	
	public CommandEvent(){
		
	}
	
	/**
	 * Checks a command and executes it if it matches the list of available commands
	 * Please note that two word commands are only supported for the time being.
	 * @param command
	 */
	public static void checkCommand(String command){
		command = command.toLowerCase();
		String[] commandAr = command.split("\\s+");
		command = commandAr[0];
		String subcommand = commandAr[1];
		switch(command){
			case "give":
				ItemGiveEvent.checkGiveCommand(subcommand);
				break;
			case "set":
				SetCommandEvent.checkSetCommand(subcommand);
				break;
			case "spawn":
				SpawnCommandEvent.checkSpawnCommand(subcommand);
		}
	}
}
