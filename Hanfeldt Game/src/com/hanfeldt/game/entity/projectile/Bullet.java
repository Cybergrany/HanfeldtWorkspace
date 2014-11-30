package com.hanfeldt.game.entity.projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.SpriteEntity;
import com.hanfeldt.game.io.Debug;

public class Bullet extends SpriteEntity {
	public static final Color COLOR = new Color(0xFF, 0x55, 0x00);
	
	public boolean hasSprite = false;
	public boolean direction;
	
	protected double angle;
	protected EntityLiving entity;
	private float speed = 3.0f;
	private static int damageValue = 10;//default damage

	
	public Bullet(EntityLiving e, int x, int y, int damage, int layer) {
		super(x, y);
		entity = e;
//		Main.getGame().getLevels().layers.get(layer).addBullet(this);
		Main.getGame().getEntityManager().addBullet(this);
		setAngle();
		damageValue = damage;
		direction = entity.getDirection();
	}
	
	public Bullet(EntityLiving e, Sprite s, int x, int y, int damage, int layer){
		super(s, x, y);
		entity = e;
//		Main.getGame().getLevels().layers.get(layer).addBullet(this);
		Main.getGame().getEntityManager().addBullet(this);
		damageValue = damage;
		sprite = s;
		hasSprite = true;
		direction = entity.getDirection();
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
		setAngle();
	}
	
	public void tick() {
		//TODO: Maybe add checkTileCollisions() here
		setVelX((float) Math.cos(angle) * speed);
		setVelY((float) Math.sin(angle) * speed);
		changeX(getVelX());
		changeY(getVelY());
		checkTileCollisions();
		if(isCollidingWithHorizTile){
			destroyBullet();
		}
		destroyBulletAtBounds();//Lyk dis? --> sure
	}
	
	@Deprecated
	public void draw(Graphics g) {
		if(!hasSprite){
			g.setColor(COLOR);
			int posX = getX() - Main.getGame().getPlayer().getX() + (Main.WIDTH /2) - (Main.TILE_SIZE /2);
			g.drawLine(posX, getY(), posX, getY());
		}else{
			sprite.draw(g, getX(), getY());
		}
	}
	
	public void destroyBulletAtBounds(){
		if (getX() < getX() - (Main.WIDTH /2) ||
			getX() > getX() + (Main.WIDTH /2) ||
			getY() < -50 || getY() > Main.getGame().getLevels().getSizeY() * Main.TILE_SIZE){
			destroyBullet();
		}
	}
	
	public void destroyBullet(){
//		Main.getGame().getLevels().layers.get(getLayer()).removeBullet(this);
		Main.getGame().getEntityManager().removeBullet(this);
	}
	
	public void onCollide(){
		destroyBullet();
	}
	
	public void onCollide(EntityLiving e){
		destroyBullet();
	}
	
	public int getDamage(){
		return damageValue;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void setSpeed(float s){
		speed = s;
	}
	
	public void changeSpeed(float s){
		speed += s;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void setAngle(){
		if(entity instanceof Player){
			try {
				Camera cam = Main.getGame().getCamera();
				Point bulletOnScreen = new Point(getX() - cam.getX(), getY() - cam.getY());
				angle =  Math.atan2(Main.mouseY - bulletOnScreen.getY(), Main.mouseX - bulletOnScreen.getX());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			try{
				angle = (float) Math.toDegrees(Math.atan2(entity.getAimX() - Main.HEIGHT / 2 - Main.TILE_SIZE,
						entity.getAimY() - (entity.getDirection() ? Main.WIDTH /2 + (Main.TILE_SIZE /2) + 3:Main.WIDTH /2 -3)));
			}catch(Exception e){
				Debug.printErrorDebug("Error setting bullet angle");
			}
		}
	}
	
	public void changeAngle(int a){
		angle += a;
	}
}
