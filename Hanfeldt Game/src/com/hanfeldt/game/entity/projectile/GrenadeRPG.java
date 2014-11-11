package com.hanfeldt.game.entity.projectile;

import com.hanfeldt.game.Values;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityLiving;

public class GrenadeRPG extends BulletLobbedExplosive{

	public GrenadeRPG(EntityLiving e, int x, int y, int layer) {
		super(e, new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 4, 1, 1), x, y, Values.bullet_damage_RPG, 200, layer);;
	}
	
	public void tick(){
		super.tick();
		if(isCollidingWithHorizTile){
			detonate();
			System.out.println("Collided");
		}
	}
	
//	public void detonate(){
//		
//	}
	
	public void onCollide(EntityLiving e){
//		destroyBullet();
		detonate();
	}
}
