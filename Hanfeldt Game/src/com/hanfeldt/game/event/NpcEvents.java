package com.hanfeldt.game.event;

import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.entity.item.ItemSpawner;
import com.hanfeldt.game.entity.item.ZombieGore;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.monsters.Zombie;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.entity.projectile.BulletLobbedExplosive;
import com.hanfeldt.io.Sound;

public class NpcEvents {

	public static final int fall_damage_id = 1;
	public static final int fall_death_id = 3;
	
	public static final int zombie_damage_id = 2;
	public static final int zombie_damage_from_bullet_id = 2;
	public static final int zombie_damage_from_sword_id = 3;
	public static final int zombie_damage_from_rpgGrenade_id = 5;
	public static final int zombie_damage_to_player = 10;
	
	private Npc npc;
	public ZombieGore zombieGore;

	public NpcEvents(Npc npc) {
		this.npc = npc;
		zombieGore = new ZombieGore(0, 0);
	}

	public void tick() {
		for (int i = 0; i < Main.getGame().bullets.size(); i++) {
			if (Main.getGame().bullets.get(i) instanceof Bullet) {
				Bullet bullet = (Bullet) Main.getGame().bullets.get(i);
				if (bulletCollided(npc, bullet)) {
					bulletHit(npc, bullet);
					bullet.onCollide(npc);
				}
			}
		}
		if (isOutsideMap(npc)) {
			killNpc(npc, Values.npc_out_of_map_id);
		}
	}

	public void damageNpc(Npc npc, int damage, int id) {
		if (id == zombie_damage_from_bullet_id) {
			Sound.playSound("zombie_hit_from_gun.wav");
		}
		if (id == zombie_damage_from_sword_id) {
			Sound.playSound("zombie_hit_from_sword.wav");
		}
		npc.changeHealth(-damage);
		if (npc.getHealth() <= 0) {
			if (npc instanceof Zombie) {
				Main.getGame().getPlayer().changeMoney(Values.money_from_zombie);
				Main.getGame().getPlayer().changeScore(Values.score_from_zombie);
				if(!isOutsideMap(npc))
				ItemSpawner.spawnItem(zombieGore.randomGore(npc.getX(), npc.getY()));
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
		Main.getGame().getNpc().remove(npc);
	}
	
	public void killNpc(Npc npc) {
		Main.getGame().getNpc().remove(npc);
	}

	public boolean isOutsideMap(Npc npc) {
		if (npc.getY() > Main.getGame().getLevels().getSizeY() * Main.TILE_SIZE) {
			return true;
		}
		return false;
	}

	public boolean isOutsideScreen(Npc npc) {
		if (npc.getX() < Main.getGame().getPlayer().getX() - (Main.WIDTH / 2)
				|| npc.getX() > Main.getGame().getPlayer().getX()
						+ (Main.WIDTH / 2)) {
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
		if(b instanceof BulletLobbedExplosive)
			return;
		Main.getGame().addGore(b.getX(), b.getY(), b.direction);
		damageNpc(npc, b.getDamage(),
				zombie_damage_from_bullet_id);
	}

	public void idle(Npc npc, float moveSpeed) {
		if (Main.timer(120)) {
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
