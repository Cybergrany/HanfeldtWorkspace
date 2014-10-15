package com.hanfeldt.game.level;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.scripting.NpcScript;

public class LevelLoader {
	
	public static int currentLevelBgAmount = 0;
	public static int[] currentLevelNpcTrigger;
	public static boolean hasStaticBg, hasForeGround;
	
	public LevelLoader(){
		
	}
	
	public static void initLevel(){
		SpriteSheet.initSheets();
	}
	
	public static void loadLevel(int level){
		PropertiesLoader.loadProperties(level);
		NpcScript.resetDialogue();
		NpcScript.setNpcTriggersAndActions(currentLevelNpcTrigger);
		Main.getGame().setLevels(new LevelStory(String.format("/images/maps/levels/level%d.png", level + 1), Main.getGame().getPlayer()));
		NpcList.loadCharacters();
		Main.getGame().getLevels().setBg(level);
	}
}
