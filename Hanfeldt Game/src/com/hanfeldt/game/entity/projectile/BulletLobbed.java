package com.hanfeldt.game.entity.projectile;

import com.hanfeldt.game.display.Sprite;

/***
 * A bullet heavily affected by gravity, which has a curved trajectory when fired.
 * @author ofeldt
 *
 */
public class BulletLobbed extends Bullet{
	float weight = .25f;//The lower the lighter the bullet, giving a larger arc
	boolean arced = false;

	public BulletLobbed(Sprite s, int x, int y, int damage) {
		super(s, x, y, damage);
		setSpeed(2.5f);
	}
	
	public void tick(){
//		System.out.println(angle);
		
		if(angle < 0 && angle >= -180){
			if(!arced){
				setVelX((float)Math.cos(angle*Math.PI/180) * getSpeed());
				setVelY((float) Math.sin(angle*Math.PI/180) * getSpeed());
			}
			changeSpeed(-((getSpeed() * (weight / 10))));
			if(getSpeed() < weight*2){
				arced = true;
			}
			if(arced){
				changeSpeed((float)(getSpeed() * (Math.pow(weight, 2))));
				setVelY(-(float) Math.sin(angle*Math.PI/180) * getSpeed());
				setVelX((float)Math.cos(angle*Math.PI/180) * getSpeed());
			}
		}else{
			setVelX((float) Math.cos(angle*Math.PI/180) * getSpeed());
			setVelY((float) Math.sin(angle*Math.PI/180) * getSpeed());
		}
		
		changeX(getVelX());
		changeY(getVelY());
		destroyBulletAtBounds();
		checkTileCollisions(this);
	}
	
	public float getWeight(){
		return weight;
	}
	
	public void setWeight(float w){
		weight = w;
	}
}
