package com.hanfeldt.game.level;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.io.ResourceManager;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.scripting.NpcScript;
import com.hanfeldt.game.state.Story;

public class LevelLoader {
	
	public static int currentLevelBgAmount = 0, currentLevelTileTrigger = 0, staticBgAmount, startingLayer;
	public static int[] currentLevelDialogueXTrigger;
	public static boolean hasStaticBg, hasForeGround;
	
	public LevelLoader(){
		
	}
	
	public static void initLevel(){
		SpriteSheet.initSheets();
	}
	
	public static void loadLevel(int level){
		PropertiesLoader.loadProperties(level);
		NpcScript.resetDialogue();
		NpcScript.setHorizontalDialogueTriggers(currentLevelDialogueXTrigger);
		if(Main.debug)
			ResourceManager.clearFile(String.format("/config/levels/level%d/",  Story.getCurrentLevel() + 1), "triggerBlocks.txt");//Clear the trigger block file if it needs to be rebuilt
		Main.getGame().setLevels(new LevelStory(String.format("/images/maps/levels/level%d.png", level + 1), Main.getGame().getPlayer()));
		Main.getGame().getLevels().initBackgrounds();
		Main.getGame().npcPreCache.loadMonsters();
		Main.getGame().npcPreCache.loadCharacters();
		//Add all entities to relevant layer. This must be done as entities are declared before the level inits
		
		Main.getGame().getPlayer().setLayer(startingLayer, true);
	}
}
