package com.hanfeldt.game.entity.projectile;

import com.hanfeldt.game.display.Sprite;

/***
 * A bullet heavily affected by gravity, which has a curved trajectory when fired.
 * @author ofeldt
 *
 */
public class BulletLobbed extends Bullet{

	public BulletLobbed(Sprite s, int x, int y, int damage) {
		super(s, x, y, damage);
	}
	
	public void tick(){
		setVelX((float) Math.cos(angle*Math.PI/180) * getSpeed());
		setVelY((float) Math.sin(angle*Math.PI/180) * getSpeed());
		changeX(getVelX());
		changeY(getVelY());
		destroyBulletAtBounds();
	}

}
