package com.hanfeldt.game.event.command;

/**
 * Why not
 * @author ofeldt
 *
 */
public class CommandNotFoundException extends Exception{
	private static final long serialVersionUID = 8606260727977717527L;//What even does this do
	
	public CommandNotFoundException(){
		super();
		System.err.println("Your command was not recognised. Please try again.\nWhen the 'help' command is implemented "
				+ "you'll be able to type 'help' and get some help.\nUntil then, just keep mashing them keys.");
	}
	
	public CommandNotFoundException(String attemptedCommand){
		super();
		System.err.println(String.format("Your command \"%s\" was not recognised. Please try again.\nWhen the 'help' command is implemented "
				+ "you'll be able to type 'help' and get some help.\nUntil then, just keep mashing them keys.", attemptedCommand));
	}
	
}
