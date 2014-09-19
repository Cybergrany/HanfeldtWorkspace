package com.hanfeldt.game.entity.projectile;

import java.awt.Color;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.SpriteEntity;

public class Bullet extends SpriteEntity {
	public static final Color COLOR = new Color(0xFF, 0x55, 0x00);
	
	public boolean hasSprite = false;
	public boolean direction;
	
	protected float angle;
	private float speed = 3.0f;
	private static int damageValue = 10;//default damage

	
	public Bullet(int x, int y, int damage) {
		super(x, y);
		setAngle();
		damageValue = damage;
	}
	
	public Bullet(Sprite s, int x, int y, int damage){
		super(s, x, y);
		damageValue = damage;
		sprite = s;
		hasSprite = true;
		direction = Main.getGame().getPlayer().getDirection();//TODO: Temporary, need to change this to get direction of holding entity
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
		setAngle();
	}
	
	public void tick() {
		//TODO: Maybe add checkTileCollisions() here
		setVelX((float) Math.cos(angle*Math.PI/180) * speed);
		setVelY((float) Math.sin(angle*Math.PI/180) * speed);
		changeX(getVelX());
		changeY(getVelY());
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
		Main.getGame().bullets.remove(this);
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
	
	public float getAngle(){
		return angle;
	}
	
	public void setAngle(){
		try {
			angle = (float) Math.toDegrees(Math.atan2(Main.mouseY - Main.HEIGHT / 2 - Main.TILE_SIZE,
					Main.mouseX - (Main.getGame().getPlayer().getDirection() ? Main.WIDTH /2 + (Main.TILE_SIZE /2) :Main.WIDTH /2)));
			System.out.println(Main.mouseY - Main.HEIGHT / 2 - Main.TILE_SIZE);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeAngle(int a){
		angle += a;
	}
}
