package com.hanfeldt.game.state;

import java.awt.Graphics;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.game.weapon.WeaponSwung;

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
			Npc npc = main.getNpc().get(i);
			npc.tick();
			//Currently not 100% necessary
//			Weapon npcWep = npc.getWeaponEquipped();
//			if(npcWep != null) npcWep.tick();
		}
		for(int i = 0; i < main.getItems().size(); i++){
			main.getItems().get(i).tick();
		}
		main.getPlayer().tick();
		camera.tick();
		Weapon wep = main.getPlayer().getWeaponEquipped();
		if(wep != null) wep.tick();
		if(main.getListener().mouseDown && wep instanceof TriggerWeapon) {
				if(!wep.isAutomatic() && !main.getListener().mouseDownLastTick){
				((TriggerWeapon) wep).tryTrigger();
			}else if(wep.isAutomatic()){
				((TriggerWeapon) wep).tryTrigger();
			}
		}
		for(int i=0; i<main.getGore().size(); i++) {
			main.getGore().get(i).tick();
		}
	}
	
	public void draw(Graphics g) {
		for(Bullet bill /* Lel */ : main.getBullets()) {
			if(!bill.hasSprite){
				camera.renderBullet(g, bill);
			}else{
				if(bill.direction){
					camera.renderBullet(g, bill, bill.getSprite());
				}else{
					camera.renderBullet(g, bill, bill.getReverseSprite());
				}
			}
		}
		main.getHud().draw(g);
		for(GoreSpawn go : main.getGore()) {
			go.render(g);
		}
		for(Npc n :  main.getNpc()){
			camera.renderEntityLiving(g,n);
		}
		for(EntityItem ei : main.getItems()){
			camera.renderEntityItem(g, ei);
		}
		
		//TODO: I'm thinking this is kind of inefficient... I've left it, because FPS seems good..
		ArrayList<Npc> npc = Main.getGame().getNpc();
		for(int i = 0; i < npc.size(); i++){
			if(npc.get(i) instanceof Npc){
				Npc npcChar = (Npc) npc.get(i);
				if(npcChar.getWeaponEquipped() != null){
					if(npcChar.getWeaponEquipped() instanceof WeaponSwung) {
						WeaponSwung tw = (WeaponSwung) (npcChar.getWeaponEquipped());
						if(tw.isTriggered()) {
							if(npcChar.getDirection()) {
								camera.renderSprite(g, npcChar.getWeaponEquipped().getReverseSprite(), npcChar.getX() - 10, npcChar.getY() +Main.TILE_SIZE /2);
							}else{
								camera.renderSprite(g, tw.getSprite(), npcChar.getX() +10, npcChar.getY() +Main.TILE_SIZE /2);
							}
						}else{
							if(!npcChar.getDirection()) {
								//Keeping this cause the way they hold it looks kinda cool
								camera.renderSprite(g, tw.getNotTriggeredSprite(), npcChar.getX() +5, npcChar.getY() +2);
							}else{
								camera.renderSprite(g, tw.getNotTriggeredSprite(), npcChar.getX() -5, npcChar.getY() +2);
							}
						}
					}else{
						if(npcChar.getDirection()) {
							camera.renderSprite(g, npcChar.getWeaponEquipped().getReverseSprite(), npcChar.getX() - 10, npcChar.getY() +Main.TILE_SIZE /2);
						}else{
							camera.renderSprite(g, npcChar.getWeaponEquipped().getSprite(), npcChar.getX() +10, npcChar.getY() +Main.TILE_SIZE /2);
						}
					}
				}
			}
		}
		Player p = main.getPlayer();
		camera.renderImage(g, p.getWalkingImage(), p.getX(), p.getY());
		if(main.getPlayer().getWeaponEquipped() != null) {
			if(p.getWeaponEquipped() instanceof WeaponSwung) {
				WeaponSwung tw = (WeaponSwung) (p.getWeaponEquipped());
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
	
}
