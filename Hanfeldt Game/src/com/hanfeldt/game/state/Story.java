package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Dialogue;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Bill;
import com.hanfeldt.game.entity.npc.Billy;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.level.LevelStory;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.M16;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.game.weapon.weapons.Sword;

public class Story extends State {
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
		if(lastLevel == 0 && level == 1) {
			main.getNpc().add(new Billy(Main.TILE_SIZE *40, Main.HEIGHT - (Main.TILE_SIZE *5)));
			currentDialogue = 0;
		}
		if(dialogue == null) {
			main.getLevels()[level].tick();
			main.getHud().tick();
			for(int i=0; i<main.getBullets().size(); i++) {
				main.getBullets().get(i).tick();
			}
			for(int i=0; i<main.getNpc().size(); i++) {
				main.getNpc().get(i).tick();
			}
			main.getPlayer().tick();
			camera.tick();
			Weapon wep = main.getPlayer().getWeaponEquipped();
			wep.tick();
			if(main.getListener().mouseDown && wep instanceof TriggerWeapon) {
				if((wep instanceof Pistol || wep instanceof Sword) && !main.getListener().mouseDownLastTick) {
					((TriggerWeapon) wep).tryTrigger();
				}else if(! (wep instanceof Pistol || wep instanceof Sword) ) {
					((TriggerWeapon) wep).tryTrigger();
				}
			}
			for(int i=0; i<main.getGore().size(); i++) {
				main.getGore().get(i).tick();
			}
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
		for(Bullet bill /* Lel */ : main.getBullets()) {
			camera.renderBullet(g, bill);
		}
		main.getHud().draw(g);
		for(GoreSpawn go : main.getGore()) {
			go.render(g);
		}
		for(Npc n :  main.getNpc()){
			camera.renderEntityLiving(g,n);
		}
		Player p = main.getPlayer();
		camera.renderImage(g, p.getWalkingImage(), p.getX(), p.getY());
		if(p.getDirection()) {
			camera.renderSprite(g, p.getWeaponEquipped().getSprite(), p.getX() +10, p.getY() +Main.TILE_SIZE /2);
		}else{
			camera.renderSprite(g, p.getWeaponEquipped().getReverseSprite(), p.getX() - 10, p.getY() +Main.TILE_SIZE /2);
		}
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
