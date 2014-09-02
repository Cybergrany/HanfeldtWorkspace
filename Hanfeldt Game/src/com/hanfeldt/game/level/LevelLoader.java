package com.hanfeldt.game.level;

import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.scripting.NpcScript;

public class LevelLoader {
	
	public static int currentLevelBgAmount = 0;
	public static ArrayList<Integer> currentLevelNpcLocation;
	public static int[] currentLevelNpcTrigger;
	public static String[] currentLevelNpcAction;
	
	public LevelLoader(){
		
	}
	
	public static void loadLevel(int level){
		PropertiesLoader.loadProperties();
		NpcScript.resetDialogue();
		NpcScript.setNpcTriggers(currentLevelNpcTrigger);
		Main.getGame().setLevels(new LevelStory(String.format("/images/maps/levels/level%d.png", level + 1), Main.getGame().getPlayer()));
		Main.getGame().getLevels().setBg(level);
	}
}
