package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Dialogue;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Bill;
import com.hanfeldt.game.entity.npc.Billy;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.level.LevelStory;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;

public class Story extends Playing {
	private Dialogue dialogue;
	private int currentDialogue = 0, totalDialogues = 0;
	private int[][] dialogueTriggerX = new int[][] {{0, 400}, {Main.TILE_SIZE *35}};
	private static int level = 0;
	private static int lastLevel = 0;
	
	public Story(Main main, Camera c) {
		super(main, c);
		//Load levels
		Level[] levels = new Level[2];
		levels[0] = new LevelStory("/images/maps/levels/level1.png", main.getPlayer());
		levels[1] = new LevelStory("/images/maps/levels/level2.png", main.getPlayer());
		main.setLevels(levels);
		
		level = 0;
		Player p = main.getPlayer();
		p.setX(Main.WIDTH /2);
		p.setY(Main.HEIGHT - Main.TILE_SIZE * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
		main.createGoreList();
		main.getNpc().add(new Bill(500, Main.HEIGHT - (Main.TILE_SIZE *4)));
	}
	
	public void tick() {
		super.tick();
		if(lastLevel == 0 && level == 1) {
			main.getNpc().add(new Billy(Main.TILE_SIZE *40, Main.HEIGHT - (Main.TILE_SIZE *5)));
			currentDialogue = 0;
			lastLevel = level;
		}
		if(dialogue == null) {
			main.getLevels()[level].tick();
		}
		if(dialogue != null && main.getListener().spaceDown && !main.getListener().spaceDownLastTick) {
			dialogue = null;
		}
		try {
			if(dialogue == null && main.getPlayer().getX() > dialogueTriggerX[level][currentDialogue]) {
				dialogue = new Dialogue(totalDialogues + ".txt");
				switch(totalDialogues) {
				case 1:
					main.getPlayer().setWeaponEquipped(new Pistol(main.getPlayer()));
					break;
				case 2:
					main.getPlayer().setWeaponEquipped(new M16(main.getPlayer()));
				}
				currentDialogue++;
				totalDialogues++;
			}
		}catch(Exception e) {}
	}
	
	public void draw(Graphics g) {
		main.getLevels()[level].render(g, camera);
		super.draw(g);
		if(!(dialogue == null)) {
			dialogue.render(g);
		}
		g.dispose();
	}
	
	public static int getCurrentLevel() {
		return level;
	}
	
	public static void setLevel(int l) {
		lastLevel = level;
		level = l;
	}
	
}
