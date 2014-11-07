package com.hanfeldt.game.properties;

import static com.hanfeldt.game.io.Debug.printDebug;
import static com.hanfeldt.game.io.Debug.printErrorDebug;
import static com.hanfeldt.game.io.Debug.printStackTraceDebug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.level.LevelLoader;
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
	
	public static void loadBlockIDs(){
		Properties prop = new Properties();
		InputStream input = null;
		
		try{
			printDebug("Reading Level properties file...\n");
			loadBlockID("/config/id/block.conf", input, prop);
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
	
	public static void loadBlockID(String path, InputStream input, Properties p) throws IOException, URISyntaxException{
		printDebug("Loading Block IDs");
		URL resourceUrl = PropertyConfig.class.getResource(path);
		if(resourceUrl == null){
			printErrorDebug("Block ID file is missing! Either reconfigure it or reinstall the game!");
		}
		File file = new File(resourceUrl.toURI());
		input = new FileInputStream(file);
		
		printDebug("Loading Block ID's from: " + path);
		p.load(input);
		
		
		
		ArrayList<String[]> blockList = new ArrayList<String[]>();
		Enumeration<Object> em = p.keys();
		
		while(em.hasMoreElements()){
			  String str = (String)em.nextElement();
			  String[] temp = p.getProperty(str).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			  blockList.add(temp);
		  }
		 
		Main.getGame().blocks = blockList;
		input.close();
	}
	
	public static void loadProperties(int level){
		Properties prop = new Properties();
		InputStream input = null;
		
		try{
			printDebug("Reading Level properties file...\n");
			loadLevelProperties(level, String.format("/config/levels/level%d/level.conf", Story.getCurrentLevel() + 1), input, prop);
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
	private static void loadLevelProperties(int level, String path, InputStream input, Properties p) throws IOException, URISyntaxException{
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
		
		//Bg amount
		printDebug("Background Amount: " + p.getProperty("bgAmount"));
		LevelLoader.currentLevelBgAmount = Integer.parseInt(p.getProperty("bgAmount"));
		
		//HasStaticBg
		printDebug("Has Static Background: " + p.getProperty("hasStaticBg"));
		LevelLoader.hasStaticBg = Boolean.parseBoolean(p.getProperty("hasStaticBg"));
		
		//HasForeGround
		printDebug("Has ForeGround: " + p.getProperty("hasForeG"));
		LevelLoader.hasForeGround = Boolean.parseBoolean(p.getProperty("hasForeG"));
		
		//Npc List
		printDebug("List of NPC's: " + p.getProperty("npcList"));
		NpcList.characterList =  p.getProperty("npcList").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
		
		//Location of NPC's
		printDebug("Location of NPC's: " + p.getProperty("npcLocation"));
		String[] temp =  p.getProperty("npcLocation").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
		NpcList.currentLevelNpcLocation = new ArrayList<Integer>();
		for(int i = 0; i <temp.length; i++){
			NpcList.currentLevelNpcLocation.add(i, Integer.parseInt(temp[i]));
		}
		
		//NPC Triggers
		printDebug("NPC Trigger: " + p.getProperty("npcXTrigger"));
		String[] temp1 = p.getProperty("npcXTrigger").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
		LevelLoader.currentLevelDialogueXTrigger = new int[temp1.length];
		for(int i = 0; i < temp1.length; i++){
			LevelLoader.currentLevelDialogueXTrigger[i] = Integer.parseInt(temp1[i]);
		}
	
		printDebug("\n--- Level Properties---\n");
		
		input.close();
	}
	
	public static void addTriggerBlock(String string, int level){
		printDebug("Adding Trigger Block " + string);
	}
}
