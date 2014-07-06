package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.weapons.Pistol;
import com.hanfeldt.game.weapon.weapons.Sword;

public abstract class Playing extends State {
	
	public Playing(Main main, Camera c) {
		super(main, c);
	}
	
	public Playing(Main main) {
		super(main, Main.getGame().getCamera());
	}
	
	public void render() {
		
	}
	
	public void tick() {
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
	
	public void draw(Graphics g) {
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
		if(p.getWeaponEquipped() instanceof Sword) {
			Sword tw = (Sword) (p.getWeaponEquipped());
			if(tw.isTriggered()) {
				if(p.getDirection()) {
					camera.renderSprite(g, tw.getSprite(), p.getX() +10, p.getY() +Main.TILE_SIZE /2);
				}else{
					camera.renderSprite(g, p.getWeaponEquipped().getReverseSprite(), p.getX() - 10, p.getY() +Main.TILE_SIZE /2);
				}
			}else{
				if(p.getDirection()) {
					camera.renderSprite(g, tw.getNotTriggeredSprite(), p.getX() +5, p.getY() +2);
				}else{
					camera.renderSprite(g, tw.getNotTriggeredSprite(), p.getX() -5, p.getY() +2);
				}
			}
		}else{
			if(p.getDirection()) {
				camera.renderSprite(g, p.getWeaponEquipped().getSprite(), p.getX() +10, p.getY() +Main.TILE_SIZE /2);
			}else{
				camera.renderSprite(g, p.getWeaponEquipped().getReverseSprite(), p.getX() - 10, p.getY() +Main.TILE_SIZE /2);
			}
		}
	}
	
}
