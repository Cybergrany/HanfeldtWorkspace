package com.hanfeldt.game.properties;

import static com.hanfeldt.io.Debug.printDebug;
import static com.hanfeldt.io.Debug.printErrorDebug;
import static com.hanfeldt.io.Debug.printStackTraceDebug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.state.Story;

/**
 * Class for any file-based operations which need to be performed.
 * @author David Ofeldt
 *
 */
public class PropertiesLoader {
	
	public PropertiesLoader(){

		
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
		NpcList.characterList =  p.getProperty("npcList").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		printDebug("Location of NPC's: " + p.getProperty("npcLocation"));
		String[] temp =  p.getProperty("npcLocation").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		Values.currentLevelNpcLocation = new ArrayList<Integer>();
		for(int i = 0; i <temp.length; i++){
			try{
				Values.currentLevelNpcLocation.add(i, Integer.parseInt(temp[i]));
			}catch(NumberFormatException nfe){
				printErrorDebug("Oh shit your config file is fucked. Boo hoo");
			}
		}
		printDebug("NPC Trigger: " + p.getProperty("npcXTrigger"));
		//TODO: this
		printDebug("NPCActions: " + p.getProperty("npcAction"));
		Values.currentLevelNpcAction = p.getProperty("npcAction").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		printDebug("\n--- Level Properties---\n");
		
		input.close();
	}
}
