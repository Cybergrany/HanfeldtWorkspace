package com.hanfeldt.game.entity;

import com.hanfeldt.game.Main;
import com.hanfeldt.io.Debug;

/**
 * A bullet fired by a non playe character.
 * @author ofeldt
 *
 */
public class BulletNpcFired extends Bullet{

	public BulletNpcFired(int x, int y, int damage, int aimX, int aimY, EntityLiving el) {
		super(x, y, damage);
		
		try{
			setAngle((float) Math.toDegrees(Math.atan2(aimX - (el.getY() + Main.TILE_SIZE),
					aimY - (el.getDirection() ? Main.WIDTH /2 + (Main.TILE_SIZE /2) + 3:Main.WIDTH /2 -3))));
		}catch(Exception e){
			Debug.printStackTraceDebug(e);
		}
	}

}
