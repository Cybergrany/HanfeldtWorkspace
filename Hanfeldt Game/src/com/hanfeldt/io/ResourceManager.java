package com.hanfeldt.io;

import static com.hanfeldt.io.Debug.printDebug;
import static com.hanfeldt.io.Debug.printErrorDebug;
import static com.hanfeldt.io.Debug.printStackTraceDebug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import com.hanfeldt.game.Values;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.state.Story;

/**
 * Class for any file-based operations which need to be performed.
 * @author David Ofeldt
 *
 */
public class ResourceManager {
	
	public ResourceManager(){

		
	}
	
	public static void loadProperties(){
		Properties prop = new Properties();
		InputStream input = null;
		
		try{
			printDebug("Reading Level properties file...\n");
			loadLevelProperties(Story.getCurrentLevel() + 1, String.format("/config/levels/level%d/level.conf", Story.getCurrentLevel() + 1), input, prop);
		}catch(IOException e){
			printErrorDebug("Error while reading properties file.");
			printStackTraceDebug(e);
		} catch (URISyntaxException e) {
			printStackTraceDebug(e);
		}finally{
			if(input != null){
				try{
					input.close();
				}catch(IOException e){
					printStackTraceDebug(e);
				}
			}
		}
	}
	
	/***
	 * Load Level properties as set in /config/levels/level1/level.conf, then update variables with them in Values.java
	 * @throws {@link URISyntaxException}
	 * @throws {@link IOException}
	 */
	public static void loadLevelProperties(int level, String path, InputStream input, Properties p) throws IOException, URISyntaxException{
		printDebug("Loading properties for level " + level);
		URL resourceUrl = PropertyConfig.class.getResource(path);
		if(resourceUrl == null){
			printErrorDebug("Level configuration file missing! Returning to main menu...");
			MenuScreenOptionAction.goBack();
		}
		File file = new File(resourceUrl.toURI());
		input = new FileInputStream(file);
		
		printDebug("Loading properties from: " + path);
		p.load(input);
		printDebug("---Level Properties---\n");
		
		printDebug("Background Amount: " + p.getProperty("bgAmount"));
		Values.currentLevelBgAmount = Integer.parseInt(p.getProperty("bgAmount"));
		printDebug("List of NPC's: " + p.getProperty("npcList"));
		Values.currentLevelNpcList =  p.getProperty("npcList").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		printDebug("Location of NPC's: " + p.getProperty("npcLocation"));
		String[] temp =  p.getProperty("npcLocation").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		Values.currentLevelNpcLocation = new ArrayList<Integer>();
		for(int i = 0; i <temp.length; i++){
			try{
				Values.currentLevelNpcLocation.add(i, Integer.parseInt(temp[i]));
			}catch(NumberFormatException nfe){
				printErrorDebug("Oh shit your config file is fucked. Boo hoo");
				System.err.println("Got a bit of an error in your config :(");
				MenuScreenOptionAction.goBack();
			}
		}
		printDebug("NPC Trigger: " + p.getProperty("npcXTrigger"));
		//TODO: this
		printDebug("NPCActions: " + p.getProperty("npcAction"));
		Values.currentLevelNpcAction = p.getProperty("npcAction").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		printDebug("\n--- Level Properties---\n");
		
		input.close();
	}
	
	/**
	 * Returns amount of image files(.png) in a specified directory. To assist in background loading.
	 * @param path directory to be searched
	 * @return resources(int) - amount of image files in the specified directory
	 */
	public int getImageResourcesInDir(String path){
		String dirWithoutSlashes = path.replaceAll("/", "");
		int level = Character.digit(dirWithoutSlashes.charAt(dirWithoutSlashes.length() -1), 10);
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/level.conf")));
			line = reader.readLine();
		}catch(Exception e) {
			e.printStackTrace();
		}
		StringTokenizer st = new StringTokenizer(line, ",");
		printDebug("Current Level: "  +level);
		for(int i=0; i<level -1; i++) {
			st.nextToken();
		}
		return Integer.parseInt(st.nextToken());
	}
	
}
