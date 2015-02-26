package com.hanfeldt.game.entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.event.NpcEvents;

public class BulletLobbedExplosive extends BulletLobbed{
	float explosiveDam, radius;
	int damage;

	public BulletLobbedExplosive(EntityLiving e, Sprite s, int x, int y, int damage, float exDamage, int layer) {
		super(e, s, x, y, damage, layer);
		explosiveDam = exDamage;
		radius = explosiveDam * .8f;
		this.damage = damage;
	}
	
	/**
	 * Detonate the {@link BulletLobbedExplosive} entity.
	 * UNFINISHED METHOD 
	 * TODO: Fix bug where entities to right of character die randomly.
	 */
	public void detonate(){
		setSpeed(0);
//		System.out.println("Detonated");
		Rectangle r = new Rectangle((int) radius, (int) radius);
		r.setLocation(getX(), getY());
		for(int i = 0; i < Main.getGame().getNpc().size(); i++){
			Npc npc = Main.getGame().getNpc().get(i);
			if(r.intersects(npc.getBounds())){
//				System.out.println("Intersection found");
//				System.out.println("xIntersection at " + npc.getX());
				npc.getEvents().damageNpc(npc, damage, NpcEvents.zombie_damage_from_rpgGrenade_id);
			}
		}
//		destroyBullet();
//		Rectangle r = new Rectangle(size, size);
//	    BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_BYTE_BINARY);
//	    for(int i = 0; i < r.width; i++){
//	    	for(int j = 0; j < r.height; j++){
//	    	    image.setRGB(i, j, Color.white.getRGB());
//	    	}
//	    }
//		sprite = new Sprite(image);
		destroyBullet();
	}
	
}
