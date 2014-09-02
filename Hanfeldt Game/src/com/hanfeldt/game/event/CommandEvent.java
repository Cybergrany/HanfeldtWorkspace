package com.hanfeldt.game.event;

public class CommandEvent {
	
	public CommandEvent(){
		
	}
	
	public static void checkCommand(String command){
		command = command.toLowerCase();
		String[] commandAr = command.split("\\s+");
		command = commandAr[0];
		String subcommand = commandAr[1];
		switch(command){
			case "give":
				ItemGiveEvent.checkGiveCommand(subcommand);
				break;
		}
	}
}
