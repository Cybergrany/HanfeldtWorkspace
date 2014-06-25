package com.hanfeldt.game.event;

import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Zombie;
import com.hanfeldt.io.Sound;

public class NpcEvents {

	private Npc npc;

	public NpcEvents(Npc npc) {
		this.npc = npc;
	}

	public void tick() {
		for (int i = 0; i < Main.getGame().bullets.size(); i++) {
			if (Main.getGame().bullets.get(i) instanceof Bullet) {
				Bullet bullet = (Bullet) Main.getGame().bullets.get(i);
				if (bulletCollided(npc, bullet)) {
					bulletHit(npc, bullet);
					bullet.destroyBullet();
				}
			}
		}
		if (isOutsideMap(npc)) {
			killNpc(npc, Values.npc_out_of_map_id);
		}
	}

	public void damageNpc(Npc npc, int damage, int id) {
		if (id == Values.zombie_damage_from_bullet_id) {
			Sound.playSound("zombie_hit_from_gun.wav");
		}
		if (id == Values.zombie_damage_from_sword_id) {
			Sound.playSound("zombie_hit_from_sword.wav");
		}
		npc.changeHealth(-damage);
		if (npc.getHealth() <= 0) {
			if (npc instanceof Zombie) {
				Main.getGame().getPlayer()
						.changeMoney(Values.money_from_zombie);
			}
			killNpc(npc, id);
		}
		if (Main.getGame().getPlayer().getDirection())
		{
			npc.setVelX(6);
		}else{
			npc.setVelX(-6);
		}
		npc.setVelY(-1);
	}

	public void killNpc(Npc npc, int id) {
		Main.npc.remove(npc);
	}

	public boolean isOutsideMap(Npc npc) {
		if (npc.getY() > Main.sizeY) {
			return true;
		}
		return false;
	}

	public boolean isOutsideScreen(Npc npc) {
		if (npc.getX() < Main.getGame().getPlayer().getX() - (Main.sizeX / 2)
				|| npc.getX() > Main.getGame().getPlayer().getX()
						+ (Main.sizeX / 2)) {
			return true;
		}
		return false;
	}

	public boolean bulletCollided(Npc npc, Bullet bullet) {
		if (npc.getBounds().intersectsLine(bullet.getX(), bullet.getY(),
				bullet.getX(), bullet.getY()))
			return true;
		return false;
	}

	public void bulletHit(Npc npc, Bullet b) {
		Main.getGame().addGore(b.getX(), b.getY());
		damageNpc(npc, Values.bullet_damage_dealt_to_zombie,
				Values.zombie_damage_from_bullet_id);
	}

	public void idle(Npc npc, float moveSpeed) {
		if (isOutsideScreen(npc) && Main.timer(120)) {
			boolean dir = new Random().nextBoolean();
			npc.setDirection(dir);
			if (dir) {
				npc.setVelX(-moveSpeed);
			} else {
				npc.setVelX(moveSpeed);
			}
		}
	}

}
