package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Dialogue;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Bill;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.game.weapon.weapons.Sword;

public class Story extends State {
	private Dialogue dialogue;
	private int currentDialogue = 0;
	private int[][] dialogueTriggerX = new int[][] {{0, 400}};
	
	public Story(Main main) {
		super(main);
		main.setLevels(new Level[2]);
		Main.setLevel(Main.getLevel());
		Player p = main.getPlayer();
		p.setX(Main.sizeX /2);
		p.setY(Main.sizeY - Main.tileSize * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
		main.createGoreList();
		main.getNpc().add(new Bill(500, Main.sizeY - (Main.tileSize *4)));
	}
	
	public void tick() {
		if(dialogue == null) {
			Main.getLevels()[Main.getLevel()].tick();
			main.getHud().tick();
			for(int i=0; i<main.getBullets().size(); i++) {
				main.getBullets().get(i).tick();
			}
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
			if(dialogue == null && main.getPlayer().getX() > dialogueTriggerX[Main.getLevel()][currentDialogue]) {
				dialogue = new Dialogue(currentDialogue + ".txt");
				switch(currentDialogue) {
				case 1:
					main.getPlayer().setWeaponEquipped(new Pistol(main.getPlayer()));
					break;
				}
				currentDialogue++;
			}
		}catch(Exception e) {}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < main.getNpc().size(); i++){
			main.getNpc().get(i).draw(g);
		}
		Main.getLevels()[Main.getLevel()].render(g);
		for(int i=0; i<main.getBullets().size(); i++) {
			main.getBullets().get(i).draw(g);
		}
		main.getHud().draw(g);
		for(GoreSpawn go : main.getGore()) {
			go.render(g);
		}
		if(!(dialogue == null)) {
			dialogue.render(g);
		}
		g.dispose();
	}
	
}
