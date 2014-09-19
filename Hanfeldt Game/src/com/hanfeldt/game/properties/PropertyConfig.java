package com.hanfeldt.game.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import com.hanfeldt.game.Main;

/**
 * Class to handle the setting of certain level properties and other property related stuff
 * @author ofeldt
 *
 */
public class PropertyConfig {
	

	private static String filenameLevel = "level.conf";
	
	public static int level = 1; //The level to configure
	
	public static int bgAmount = 4;//How many layers of dynamic parallax backgrounds this level has
	public static boolean hasStaticBackground = true;//If level has static background. Will support multiple if needed
	public static String[]  npcList  = new String[] {"Bill"};//List of NPC's in this Level
	public static String[] npcLocation = new String[] {"500", Integer.toString(Main.HEIGHT - (Main.TILE_SIZE*4))};//Where the NPC's are
	public static String[] npcXTrigger = new String[] {"400"};//Where the NPC is triggered
	public static String[] npcAction = new String[]{"give", "M16"};//What happens(Apart from Dialogue) when the NPC is triggered
	
	public static void main(String[] args){
		
		Properties properties = new Properties();
		OutputStream output = null;
		try{
			System.out.println("Attempting to write properties file...");
			setLevelProperties(String.format("/config/levels/level%d/%s", level, filenameLevel), output, properties);
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
		URL resourceUrl = PropertyConfig.class.getResource(levelPath);
		
		if(resourceUrl == null){
			System.out.println("File not found: " + levelPath + "\nPlease create this file, as I am too lazy to code in a file creator");
			System.exit(0);
		}
		File file = new File(resourceUrl.toURI());
		
		output = new FileOutputStream(file);
		
		System.out.println("Writing to properties file to " + levelPath);
		
		p.setProperty("bgAmount", Integer.toString(bgAmount));
		p.setProperty("hasStaticBg", Boolean.toString(hasStaticBackground));
		p.setProperty("npcList", Arrays.toString(npcList));
		p.setProperty("npcLocation", Arrays.toString(npcLocation));
		p.setProperty("npcXTrigger", Arrays.toString(npcXTrigger));
		p.setProperty("npcAction", Arrays.toString(npcAction));
		
		p.store(output, null);
		
		System.out.println("Write successful!");
		
		output.close();
		
		System.out.println("Closed successfully");
	}

}
