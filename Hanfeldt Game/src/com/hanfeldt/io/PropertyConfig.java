package com.hanfeldt.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import com.hanfeldt.game.Main;

/**
 * Class to handle the setting of certain level properties and other property related stuff
 * @author ofeldt
 *
 */
public class PropertyConfig {
	

	private static String filename = "level.conf";
	
	public static int level = 1; //The level to configure
	public static int bgAmount = 4;//How many layers of backgrounds this level has
	public static String[]  npcList  = new String[] {"Bill"};//List of NPC's in this Level
	public static int[][] npcLocation = new int[][] {{500, Main.HEIGHT - (Main.TILE_SIZE*4)}};//Where the NPC's are
	public static int[][] npcXTrigger = new int[][] {{0, 400}, {Main.TILE_SIZE * 35}};//Where the NPC is triggered
	public static String[][] npcAction = new String[][]{{"give", "Pistol"}};//What happens(Apart from Dialogue) when the NPC is triggered
	
	public static void main(String[] args){
		
		Properties properties = new Properties();
		OutputStream output = null;
		try{
			System.out.println("Attempting to write properties file...");
			setLevelProperties(String.format("/config/levels/level%d/%s", level, filename), output, properties);
		}catch(IOException e){
			System.err.println("Something went wrong dammit");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}finally{
			if(output != null){
				try{
					output.close();
					System.out.println("Output closed succesfully");
				}catch(IOException e){
					System.err.println("I have no idea how, but it fucked up when closing the output.");
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void setLevelProperties(String levelPath, OutputStream output, Properties p) throws IOException, URISyntaxException{
		System.out.println(levelPath);
		URL resourceUrl = PropertyConfig.class.getResource(levelPath);
		System.out.println(resourceUrl);
		File file = new File(resourceUrl.toURI());
		
		output = new FileOutputStream(file);
		
		System.out.println("Writing to properties file to " + levelPath);
		
		p.setProperty("bgAmount", Integer.toString(bgAmount));
		p.setProperty("npcList", npcList.toString());
		p.setProperty("npcLocation", npcLocation.toString());
		p.setProperty("npcXTriger", npcXTrigger.toString());
		p.setProperty("npcAction", npcAction.toString());
		
		System.out.println("Write successful!");
		
		output.close();
	}

}
