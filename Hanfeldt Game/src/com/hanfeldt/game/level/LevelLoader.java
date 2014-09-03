package com.hanfeldt.game.level;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Hud;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.scripting.NpcScript;

public class LevelLoader {
	
	private SpriteSheet spriteSheet;
	private Player player;
	private Sprite character;
	private Hud hud;
	
	public static int currentLevelBgAmount = 0;
	public static int[] currentLevelNpcTrigger;
	public static String[] currentLevelNpcAction;
	
	public LevelLoader(){
		
	}
	public static void loadLevel(int level){
		PropertiesLoader.loadProperties();
		NpcScript.resetDialogue();
		NpcScript.setNpcTriggersAndActions(currentLevelNpcTrigger, currentLevelNpcAction);
		Main.getGame().setLevels(new LevelStory(String.format("/images/maps/levels/level%d.png", level + 1), Main.getGame().getPlayer()));
		NpcList.loadCharacters();
		Main.getGame().getLevels().setBg(level);
	}
}
