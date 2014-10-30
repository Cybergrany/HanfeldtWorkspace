package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.io.Debug;
import com.hanfeldt.game.level.LevelLoader;
import com.hanfeldt.game.scripting.NpcScript;

public class Story extends Playing {
	private static int level = 0;
	private static int lastLevel = 0;
	
	private NpcScript script;
	
	public Story(Main main, Camera c) {
		super(main, c);
		
		//Load levels
		LevelLoader.loadLevel(level);
		
		Player p = main.getPlayer();
		p.setHealth(Player.maxHealth);
		main.createGoreList();
		script = new NpcScript(main, p);
	}
	
	public void tick() {
		Player player = main.getPlayer();
		if(player.levelFinished){
			if(level +1 >= 2) {
				Debug.printErrorDebug("Add levelamount chack to Story.java, line 51");
				//Win code
				Main.getGame().setState(new GameWon(Main.getGame()));
			}else{
				Story.addLevel(level);
				LevelLoader.loadLevel(level);
				Main.getGame().respawnPlayer();
//				player.setX(0);
//				player.setY(Main.HEIGHT - Main.TILE_SIZE *2 - player.getSizeY());
				player.levelFinished = false;
			}
		}
		if(lastLevel == 0 && level == 1) {
//			main.getNpc().add(new Billy(Main.TILE_SIZE *40, Main.HEIGHT - (Main.TILE_SIZE *5)));
			lastLevel = level;
		}
		if(NpcScript.getDialogue() == null) {
			main.getLevels().tick();
			super.tick();
		}
		script.tick();
	}
	
	public void draw(Graphics g) {
		main.getLevels().render(g, camera);
		super.draw(g);
		//TODO: Call renderfore when done
		script.draw(g);
		g.dispose();
	}
	
	public static int getCurrentLevel() {
		return level;
	}
	
	public static void addLevel(int l) {
		lastLevel = level;
		level = l +1;
	}
	
}
