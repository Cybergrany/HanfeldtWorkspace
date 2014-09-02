package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.characters.Bill;
import com.hanfeldt.game.entity.npc.characters.Billy;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.level.LevelLoader;
import com.hanfeldt.game.level.LevelStory;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.scripting.Dialogue;
import com.hanfeldt.game.scripting.NpcScript;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;

public class Story extends Playing {
	private static int level = 0;
	private static int lastLevel = 0;
	
	private NpcScript script;
	
	public Story(Main main, Camera c) {
		super(main, c);

		PropertiesLoader.loadProperties();
		
		//Load levels
		LevelLoader.loadLevel(level);
		Level[] levels = new Level[2];
		levels[0] = new LevelStory("/images/maps/levels/level1.png", main.getPlayer());
		levels[1] = new LevelStory("/images/maps/levels/level2.png", main.getPlayer());
		main.setLevels(levels);
		
		level = 0;
		Player p = main.getPlayer();
//		p.setX(Main.WIDTH /2);
		p.setY(Main.HEIGHT - Main.TILE_SIZE * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
		main.createGoreList();
		main.getNpc().add(new Bill(500, Main.HEIGHT - (Main.TILE_SIZE *4)));
		script = new NpcScript(main, p);
	}
	
	public void tick() {
		Player player = main.getPlayer();
		if(player.levelFinished){
			if(level +1 >= Main.getGame().getLevels().length) {
				//Win code
				Main.getGame().setState(new GameWon(Main.getGame()));
			}else{
				Story.addLevel(level);
				main.getLevels()[level].setBg(level);
				player.setX(0);
				player.setY(Main.HEIGHT - Main.TILE_SIZE *2 - player.getSizeY());
				player.levelFinished = false;
			}
		}
		if(lastLevel == 0 && level == 1) {
			main.getNpc().add(new Billy(Main.TILE_SIZE *40, Main.HEIGHT - (Main.TILE_SIZE *5)));
//			currentDialogue = 0;
			lastLevel = level;
		}
		if(NpcScript.getDialogue() == null) {
			main.getLevels()[level].tick();
			super.tick();
		}
		script.tick();
	}
	
	public void draw(Graphics g) {
		main.getLevels()[level].render(g, camera);
		super.draw(g);
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
