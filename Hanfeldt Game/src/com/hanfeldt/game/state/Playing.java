package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.weapon.TriggerWeapon;

public class Playing extends State {
	
	public Playing(Main main) {
		super(main);
		Player p = main.getPlayer();
		p.setX(Main.sizeX /2);
		p.setY(Main.sizeY - Main.tileSize * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
	}
	
	public void tick() {
		Main.getLevels()[Main.getLevel()].tick();
		main.getHud().tick();
		for(int i=0; i<main.getBullets().size(); i++) {
			main.getBullets().get(i).tick();
		}
		main.getPlayer().getWeaponEquipped().tick();
		if(!main.getListener().mouseDownLastTick && main.getListener().mouseDown && main.getPlayer().getWeaponEquipped() instanceof TriggerWeapon) {
			((TriggerWeapon) main.getPlayer().getWeaponEquipped()).tryTrigger();
		}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < main.getNpc().size(); i++){
			main.getNpc().get(i).draw(g);
		}
		Main.getLevels()[Main.getLevel()].render(g);
		main.getHud().draw(g);
		for(int i=0; i<main.getBullets().size(); i++) {
			main.getBullets().get(i).draw(g);
		}
		g.dispose();
	}

}
