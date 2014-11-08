package com.hanfeldt.game.state;

import java.awt.Graphics;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.particles.GoreSpawn;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.level.Layer;
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
		for(GoreSpawn go : main.getGore()) {
//			go.render(g);
			camera.renderGore(g, go);
		}
		for(Npc n :  main.getNpc()){
			camera.renderEntityLiving(g,n);
		}
		for(EntityItem ei : main.getItems()){
			camera.renderEntityItem(g, ei);
		}
	
		Player p = main.getPlayer();
		camera.renderImage(g, p.getWalkingImage(), p.getX(), p.getY());//Draw player
		
		for(Layer l : main.getLevels().layers){
			l.getBackground().draw(g);
		}
		
//		camera.drawRectBorder(g, 0, Main.WIDTH, 40, 110, Color.BLACK, true);

		main.getHud().draw(g);
	}
	
}
