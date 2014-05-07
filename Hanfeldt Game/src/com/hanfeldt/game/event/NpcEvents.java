package com.hanfeldt.game.event;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.io.Sound;

public class NpcEvents {
	
	private Npc npc;
	
	public NpcEvents(Npc npc){
		this.npc = npc;
	}
	
	public void tick(){
		for(int i = 0; i < Main.getGame().bullets.size(); i++){
			if(Main.getGame().bullets.get(i) instanceof Bullet){
				Bullet bullet = (Bullet) Main.getGame().bullets.get(i);
				if(bulletCollided(npc, bullet)){
					if(Main.debug)
					System.err.println(bullet.getBounds());
					bulletHit(npc);
					bullet.destroyBullet();
				}
			}
		}
	}
	
	public void damageNpc(Npc npc, int damage, int id){
		if(id == Values.zombie_damage_from_bullet_id){
			npc.changeHealth(-damage);
			Sound.playSound("zombie_hit_from_gun.wav");
		}
		if(npc.getHealth() <= 0){
			killNpc(npc, id);
		}
	}
	
	public void killNpc(Npc npc, int id){
		if(id == Values.zombie_damage_from_bullet_id){
			//TODO: A zombie death cry + zombie falling over
			Main.npc.remove(npc);
		}
	}
	
	public boolean bulletCollided(Npc npc, Bullet bullet){
		if (bullet.getBounds().intersects(npc.getBounds()) && npc.getBounds().intersects(bullet.getBounds()))
			return true;
		if(Main.debug)
		System.out.println("Miss");
		return false;
	}
	
	public void bulletHit(Npc npc){
		for(int i = 0; i < Main.npc.size(); i++){
			if(Main.npc.get(i) instanceof Zombie){
				damageNpc(Main.npc.get(i), Values.bullet_damage_dealt_to_zombie, Values.zombie_damage_from_bullet_id);
			}
		}
	}
}
