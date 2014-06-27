package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.level.LevelArcade;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.game.weapon.weapons.Sword;

public class Arcade extends State {
	
	public Arcade(Main main) {
		super(main);
		main.setArcadeLevel(new LevelArcade(main.getPlayer()));
		Player p = main.getPlayer();
		p.setX(Main.sizeX /2);
		p.setY(Main.sizeY - Main.tileSize * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
		Main.getGame().createGoreList();
	}
	
	public void tick() {
		Main.getArcadeLevels().tick();
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
		for(int i=0; i<Main.getGame().getGore().size(); i++) {
			Main.getGame().getGore().get(i).tick();
		}
		if(main.getListener().shopButtonDown && !main.getListener().shopButtonDownLastTick) {
			main.setState(new Shop(main));
		}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < main.getNpc().size(); i++){
			main.getNpc().get(i).draw(g);
		}
		Main.getArcadeLevels().render(g);
		for(int i=0; i<main.getBullets().size(); i++) {
			main.getBullets().get(i).draw(g);
		}
		main.getHud().draw(g);
		for(GoreSpawn go : Main.getGame().getGore()) {
			go.render(g);
		}
		g.dispose();
	}

}