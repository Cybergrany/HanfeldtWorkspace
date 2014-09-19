package com.hanfeldt.game.event.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Emm... Does this need a thread? Maybe.
 * Do I know a more efficient way of doing this? No.
 * Did I research a more efficient way of doing this? No.
 * Will I improve this so this doesn't hog a whole thread to itself? Yes.
 * When? Later.
 * @author ofeldt
 *
 */
public class ConsoleHandler implements Runnable{
	
	boolean running = false;

    BufferedReader br;
	
	public void start(){
		br = new BufferedReader(new InputStreamReader(System.in));
		Thread consoleThread = new Thread(this);
		consoleThread.start();
	}

	@Override
	public void run() {
		running = true;
		while(running){
			try{
				CommandEvent.checkCommand(br.readLine());
			}catch(CommandNotFoundException e){/*Hooray for internal error handling*/}catch(IOException e){
				System.err.println("Woah your command screwed things up");
			}
		}
	}

}
