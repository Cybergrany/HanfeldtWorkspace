package com.hanfeldt.game.io;

import com.hanfeldt.game.Main;

/**
 * Mainly created to prevent the need to check for Main.debug every time you want something outputted
 * to the console in debug mode
 * 
 * I will also at some stage add error logging to file
 * 
 * yolo
 * @author ofeldt
 *
 */
public class Debug {
	
	public static void printDebug(Object o){
		if(Main.debug){
			System.out.println(o);
		}
	}
	
	public static void printErrorDebug(Object o){
		if(Main.debug){
			System.err.println(o);
		}
	}
	
	public static void printStackTraceDebug(Exception e){
		if(Main.debug){
			e.printStackTrace();
		}
	}
}
