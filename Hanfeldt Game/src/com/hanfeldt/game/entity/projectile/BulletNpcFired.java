package com.hanfeldt.game.entity.projectile;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.io.Debug;

/**
 * A bullet fired by a non playe character.
 * @author ofeldt
 *
 */
public class BulletNpcFired extends Bullet{
	
	private EntityLiving ent;

	public BulletNpcFired(int x, int y, int damage, int aimX, int aimY, EntityLiving el) {
		super(x, y, damage);
		
		try{
			angle = (float) Math.toDegrees(Math.atan2(aimX - Main.HEIGHT / 2 - Main.TILE_SIZE,
					aimY - (el.getDirection() ? Main.WIDTH /2 + (Main.TILE_SIZE /2) + 3:Main.WIDTH /2 -3)));
		}catch(Exception e){
			Debug.printErrorDebug("Error setting bullet angle");
		}
//		System.out.println(angle);
		ent = el;
	}
	
	public void setAngle(){
		//Deliberately empty, to override setAngle() from Bullet.java. This stops mouse input from being used.
	}
	
	public void draw(Graphics g) {
		g.setColor(COLOR);
		int posX = getX() - ent.getX() + (Main.WIDTH /2) - (Main.TILE_SIZE /2);
		g.drawLine(posX, getY(), posX, getY());
	}

}
