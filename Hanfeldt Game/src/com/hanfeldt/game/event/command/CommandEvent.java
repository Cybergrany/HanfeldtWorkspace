package com.hanfeldt.game.event.command;


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
				ItemGiveEvent.checkGiveCommand(subcommand);
				break;
			case "set":
				SetCommandEvent.checkSetCommand(subcommand);
				break;
			case "spawn":
				SpawnCommandEvent.checkSpawnCommand(subcommand);
				break;
			default:
				throw new CommandNotFoundException();
		}
//		throw new CommandNotFoundException();
	}
}
